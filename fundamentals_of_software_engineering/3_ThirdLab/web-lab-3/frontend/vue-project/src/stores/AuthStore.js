import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    auth_header: ''
  }),

  actions: {
    async refresh_token() {
      try {
        const response = await fetch("/api/auth/refresh")

        if (!response.ok) {
          return false
        }

        const token = response.headers.get("Authorization")
        if (token) {
          this.auth_header = token
        }

        return true
      } catch (err) {
        console.error("Ошибка при обновлении токена:", err)
        return false
      }
    },

    async check_auth() {
        try {
            const response = await fetch("/api/auth/ping", {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': this.auth_header
                }
            }
            )



            if (response.ok) {
                return true
            } else {
                const a = await this.refresh_token()
                return a
            }
        } catch (err) {
            console.log(err)
            return false
        }
    },

    async login(username, password) {
        const response = await fetch("/api/auth/login",
            {
                method: "POST",
                headers: {
                    'Content-type' : 'application/json'
                },
                body: JSON.stringify({username: username, password: password})
            }
        )
        if (!response.ok) {
            throw new Error(response.status)
        }
        this.auth_header = await response.headers.get("Authorization")
    },

    async register(username, password) {
        const response = await fetch("/api/auth/register",
            {
                method: "POST",
                headers: {
                    'Content-type' : 'application/json'
                },
                body: JSON.stringify({username: username, password: password})
            }
        )
        if (!response.ok) {
            throw new Error(response.status)
        }
        this.auth_header = await response.headers.get("Authorization")
    },

    async logout() {
    try {
        const response = await fetch("/api/auth/logout", {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': this.auth_header
                }
            }
        )
        this.auth_header = '';
        if (response.ok) return true
      } catch {
        return false
      }
    }
  }
})
