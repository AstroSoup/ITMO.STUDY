<script>
import { mapState } from 'pinia'
import { useAuthStore } from '@/stores/AuthStore.js'
import { useHistoryStore } from '@/stores/HistoryStore.js'

export default {
  name: 'HeaderBar',
  computed: {
    ...mapState(useAuthStore, ['auth_header']),
    isAuth() {
      return this.auth_header !== ''
    }
  },
  methods: {
    logout() {
      const authStore = useAuthStore()
      authStore.logout()
      authStore.auth_header = ''

      const historyStore = useHistoryStore()
      historyStore.clear()
      historyStore.clear_input()

      this.$router.push({ name: 'start' })
    }
  }
}
</script>

<template>
  <header class="header">
    <div class="header-inner">
      <div class="header-info">
        <div class="name">
          <h2>Горин Семён Дмитриевич P3208</h2>
        </div>
        <div class="discipline">
          <h2>Веб-программирование</h2>
        </div>
        <div class="work">
          <h2>Лабораторная работа № 3</h2>
        </div>
      </div>
      <div class="auth-actions">
        <button v-if="isAuth" @click="logout">Выйти</button>
      </div>
    </div>
  </header>
</template>

<style scoped>
.header {
  background: linear-gradient(90deg, red, orange, yellow, green, blue, indigo, violet);
  color: white;
  width: 100%;
}

.header-inner {
  max-width: var(--max-width);
  margin: 0 auto;
  padding: 15px;
  display: flex;
  flex-direction: column; /* tablet/desktop layout */
  align-items: center;
  justify-content: center;
  text-align: center;
  gap: 12px;
}

.header-info {
  display: flex;
  flex-direction: row;
  justify-content: center;
  flex-wrap: wrap;
  gap: 18px;
}

.header-info h2 {
  font-size: 1.1rem;
  font-weight: 600;
  margin: 0;
  white-space: nowrap;
}

button {
  background: transparent;
  border: 1px solid rgba(255, 255, 255, 0.7);
  padding: 8px 12px;
  color: white;
  border-radius: 8px;
  font-weight: 500;
  transition: background 0.2s;
}

button:hover {
  background: rgba(255, 255, 255, 0.15);
  cursor: pointer;
}

/*Mobile (<885px)*/
@media (max-width: 884px) {
  .header-inner {
    flex-direction: column;
    gap: 6px;
    text-align: center;
    align-items: center;
  }

  .header-info {
    flex-direction: column;
    gap: 4px;
  }

  .header-info h2 {
    font-size: 1rem;
  }

  .auth-actions {
    margin-top: 6px;
  }

  button {
    font-size: 0.9rem;
    padding: 6px 10px;
  }
}
</style>
