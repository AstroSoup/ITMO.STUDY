<script>
import PopupWindow from '@/common/PopupWindow.vue';
import { useAuthStore } from '@/stores/AuthStore.js'

export default {
  name: 'StartPage',
  components: {PopupWindow},
  data() {
    return {
      loginValue: '',
      passwordValue: '',
      repeatPasswordValue: '',
      isRegisterMode: false,
      errorMsg: '',
      errorTitle: ''
    }
  },
  methods: {
    async login() {
      try {
        const auth = useAuthStore()
        await auth.login(this.loginValue, this.passwordValue)

        this.$router.push('/app')
      } catch (error) {
        if (error.message === '401') {
          this.errorTitle = 'Ошибка входа'
          this.errorMsg = 'Возможно вы неверно ввели имя пользователя или пароль.'
          return
        }
        this.errorTitle = 'Ошибка входа'
        this.errorMsg = 'Во время аутентификации произошла непредвиденная ошибка. Пожалуйста, попробуйте позднее.' 
      }
    },
    async register() {
      if (this.passwordValue !== this.repeatPasswordValue) {
        this.errorTitle = 'Ошибка регистрации'
        this.errorMsg = 'Введенные вами пароли не совпадают.'
        return
      }
      try {
        const auth = useAuthStore()
        await auth.register(this.loginValue, this.passwordValue)
        this.$router.push('/app')
      } catch (error) {
        if (error.message === '409') {
          this.errorTitle = 'Ошибка регистрации'
          this.errorMsg = 'Пользователь с таким именем уже существует. Пожалуйста, выберете другое имя.'
          return
        }
        this.errorTitle = 'Ошибка регистрации'
        this.errorMsg = 'При регистрации произошла непредвиденная ошибка. Пожалуйста, попробуйте позднее.'
      }
    },
    async ping() {
      const auth = useAuthStore()
      alert(await auth.check_auth())
    },
    toggleMode() {
      this.isRegisterMode = !this.isRegisterMode
      this.loginValue = ''
      this.passwordValue = ''
      this.repeatPasswordValue = ''
    }
  },
  created() {
    const auth = useAuthStore()
    if (auth.check_auth()) {
      this.$router.push('/app')
    }
  }
}
</script>


<template>
    <div class="start-page container">
      <PopupWindow v-if="errorMsg" :message="errorMsg" :title="errorTitle" @acknowledged="errorMsg=''" />
        <div class="card">
            <h2>{{ isRegisterMode ? 'Регистрация' : 'Вход в приложение' }}</h2>

            <form @submit.prevent="isRegisterMode ? register() : login()">
                <label>Логин
                    <input v-model="loginValue" required />
                </label>

                <label>Пароль
                    <input v-model="passwordValue" type="password" required />
                </label>

                <label v-if="isRegisterMode">Повторите пароль
                    <input v-model="repeatPasswordValue" type="password" required />
                </label>

                <div class="actions">
                    <button type="submit">
                        {{ isRegisterMode ? 'Зарегистрироваться' : 'Войти' }}
                    </button>
                </div>
            </form>

            <p class="switch-mode">
                {{ isRegisterMode ? 'Уже есть аккаунт?' : 'Нет аккаунта?' }}
                <a href="#" @click.prevent="toggleMode">
                    {{ isRegisterMode ? 'Войти' : 'Зарегистрироваться' }}
                </a>
            </p>
        </div>
    </div>
</template>

<style scoped>
.container{
    width:100%;
    max-width:720px;
    margin:0 auto;
}
h2{
    margin-top:0;
}
label{
    display:block;
    margin:12px 0;
}
input{
    width:100%;
    padding:10px;
    border-radius:6px;
    border:1px solid #ccc;
}
.actions{
    margin-top:12px;
}
button{
    padding:10px 16px;
    border-radius:8px;
    border:none;
    background:#2b6cb0;
    color:white;
    cursor:pointer;
}
button:hover{
    background:#2c5282;
}
.switch-mode{
    text-align:center;
    margin-top:16px;
}
.switch-mode a{
    color:#2b6cb0;
    cursor:pointer;
    text-decoration:none;
    font-weight:bold;
}
.switch-mode a:hover{
    text-decoration:underline;
}
</style>
