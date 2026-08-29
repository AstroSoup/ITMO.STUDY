import { defineStore } from 'pinia'
import { useAuthStore } from "./AuthStore.js"

export const useHistoryStore = defineStore('history', {
  state: () => ({
    options: [-5,-4,-3,-2,-1,0,1,2,3],
    x: '',
    y: '',
    r: '',
    history: []
  }),

  getters: {
    reversedHistory: (state) => state.history.slice().reverse(),
    hitsOnly: (state) => state.history.filter(item => item.hit)
  },

  actions: {
    async set_history_from_backend() {
      try {
        const authStore = useAuthStore()
        const response = await fetch('/api/hit', {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': authStore.auth_header
          }
        })

        if (response.status === 401 && authStore.refresh_token()) {
          this.addRecord(record)
        }

        if (!response.ok) {
          throw new Error(`Ошибка запроса: ${response.status}`)
        }

        const data = await response.json()
        this.history = Array.isArray(data) ? data : data.data || []
      } catch (error) {
        console.error('Ошибка при загрузке истории:', error)
      }
    },

    async addRecord() {
      if (this.options.includes(this.r) && this.r > 0 && this.options.includes(this.x) && this.y !== '' && this.y >= -3 && this.y <= 5) {
        try {
          const authStore = useAuthStore()
          const response = await fetch('/api/hit', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': authStore.auth_header
            },
            body: JSON.stringify({x: this.x, y: this.y, r: this.r})
          })
          
          if (response.status === 401 && authStore.refresh_token()) {
            this.addRecord()
          }

          if (!response.ok) {
            throw new Error(`Ошибка при добавлении записи: ${response.status}`)
          }

          const newRecord = await response.json()
          this.history.push(newRecord)
        } catch (error) {
          console.error('Ошибка при добавлении записи:', error)
        }
      } else {
        throw new Error("invalid parameters")
      }


    },
    clear() {
      this.history = []
    },
    clear_input() {
      this.x = ''
      this.y = ''
      this.r = ''
    }
  }
})
