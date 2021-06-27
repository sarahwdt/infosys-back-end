<template>
  <div class="w3-modal w3-container" style="display: block;">
    <div v-if="!loading" class="w3-modal-content">
      <header class="w3-container w3-xxlarge w3-teal"><h2>Регистрация</h2></header>
      <span v-on:click="close" class="w3-button w3-teal w3-display-topright">&times;</span>
      <form class="w3-container w3-padding-bottom" @submit.prevent="registration">
        <label style="width: 100%"><b>Логин</b>
          <div v-show="username.length < 5" class="w3-panel w3-pale-red">Логин должен содержать минимум 5 символов</div>
          <input class="w3-input" size="" style="width: 100%" type="text"
                 :class="{ 'w3-pale-red': submitted && !username }"
                 v-model="username" name="username">
        </label>
        <label style="width: 100%"><b>Пароль</b>
          <div v-show="password.length < 5" class="w3-panel w3-pale-red">Пароль должен содержать минимум 5 символов</div>
          <input class="w3-input" size="" style="width: 100%" type="password"
                 :class="{ 'w3-pale-red': submitted && !password }"
                 v-model="password" name="password">
        </label>
        <label style="width: 100%"><b>Подтверждение пароля</b>
          <div v-show="password !== passwordConfirm" class="w3-panel w3-pale-red">Пароли не совпадают</div>
          <input class="w3-input" size="" style="width: 100%" type="password"
                 :class="{ 'w3-pale-red': submitted && !passwordConfirm }"
                 v-model="passwordConfirm" name="passwordConfirm">
        </label>
        <button :disabled="disabledSubmit" class="w3-button w3-block w3-teal w3-section w3-padding" type="submit">
          Зарегистрироваться
        </button>
      </form>
    </div>
    <div v-else class="loader w3-display-middle"></div>
  </div>
</template>

<script>
import {auth, axios} from "@/_helpers";

export default {
  name: "LoginModal",
  data() {
    return {
      username: '',
      password: '',
      passwordConfirm:'',
      submitted: false,
      loading: false,
    }
  },
  methods: {
    registration() {
      this.submitted = true;
      this.loading = true;

      axios.post('/auth/reg', {
        login: this.username,
        password: this.password,
        passwordConfirm: this.passwordConfirm
      }).then(() => {
        auth.update()
        this.close()
      }).catch(() => {
        this.submitted = false;
      }).finally(() => this.loading = false)
    },
    close() {
      this.$emit('close')
    }
  },
  computed:{
    disabledSubmit: function (){
      return this.loading || this.submitted
          || !this.password || this.password.length < 5 || this.password !== this.passwordConfirm
          || !this.username || this.username.loading < 5
    }
  }
}
</script>

<style scoped>
.loader {
  border: 16px solid #f3f3f3; /* Light grey */
  border-top: 16px solid teal; /* Blue */
  border-radius: 50%;
  width: 120px;
  height: 120px;
  animation: spin 0.3s linear infinite;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>