<template>
  <div>
    <nav v-if="!admin" class="w3-bar w3-teal w3-container w3-left-align w3-large" style="padding: 0; z-index: 10">
      <router-link to="/" class="w3-bar-item w3-button">
        <span v-on:click="auth.update">Фитнес</span></router-link>
      <router-link v-if="auth.hasPrivilege('READ_SESSION')"
                   to="/session" class="w3-bar-item w3-button w3-hide-small w3-hover-white">
        <span v-on:click="auth.update">Занятия</span></router-link>
      <router-link v-if="auth.hasPrivilege('READ_INSTRUCTOR')"
                   to="/instructor" class="w3-bar-item w3-button w3-hide-small w3-hover-white">
        <span v-on:click="auth.update">Тренеры</span></router-link>
      <router-link v-if="auth.hasPrivilege('READ_CLIENT')"
                   to="/client" class="w3-bar-item w3-button w3-hide-small w3-hover-white">
        <span v-on:click="auth.update">Клиенты</span></router-link>

      <span v-if="auth.hasPrivilege('READ_ROLE')
    || auth.hasPrivilege('READ_USER')
    || auth.hasPrivilege('READ_PRIVILEGE')"
            class="w3-bar-item w3-button w3-hide-small w3-hover-white"
            :class="{'w3-blue-gray':admin}"
            v-on:click="switchAdmin">Администрирование</span>
      <router-link to="/about" class="w3-bar-item w3-button w3-hide-small w3-hover-white">
        <span v-on:click="auth.update">О нас</span></router-link>

      <span v-if="!store.state.user" v-on:click="switchLoginModal"
            class="w3-bar-item w3-button w3-hide-small w3-hover-white w3-right">
          Войти</span>
      <span v-if="!store.state.user" v-on:click="switchRegModal"
            class="w3-bar-item w3-button w3-hide-small w3-hover-white w3-right">
          Зарегистрироваться</span>
      <span v-if="store.state.user" class="w3-bar-item w3-button w3-hide-small w3-hover-white w3-right"
            v-on:click="logout">Выйти</span>
      <span v-if="store.state.user"
            v-on:click="switchUserDataModal"
            class="w3-bar-item w3-button w3-hide-small w3-hover-white w3-right"
      >{{ store.state.user.login }}</span>
    </nav>


    <nav v-else class="w3-bar w3-blue-gray w3-container w3-left-align w3-large">
      <span class="w3-bar-item w3-button w3-hide-small w3-hover-white" v-on:click="switchAdmin">Назад</span>
      <router-link v-if="auth.hasPrivilege('READ_PRIVILEGE')"
                   to="/privilege" class="w3-bar-item w3-button w3-hide-small w3-hover-white">
        <span v-on:click="auth.update">Привилегии</span></router-link>
      <router-link v-if="auth.hasPrivilege('READ_ROLE')"
                   to="/role" class="w3-bar-item w3-button w3-hide-small w3-hover-white">
        <span v-on:click="auth.update">Роли</span></router-link>
      <router-link v-if="auth.hasPrivilege('READ_USER')"
                   to="/user" class="w3-bar-item w3-button w3-hide-small w3-hover-white">
        <span v-on:click="auth.update">Пользователи</span></router-link>
    </nav>
    <login-modal v-if="showLoginModal" @close="switchLoginModal"></login-modal>
    <reg-modal v-if="showRegModal" @close="switchRegModal"></reg-modal>
    <user-data v-if="showUserDataModal" @close="switchUserDataModal"></user-data>

    <div v-if="store.state.error !== ''" class="w3-modal-content w3-display-container w3-display-bottomright"
         style="display: block; z-index:100; min-width: 15%; max-width: 200px">
      <header class="w3-container w3-red">
        <span v-on:click="store.commit('error', '')"
              class="w3-button w3-display-topright">&times;</span>
        <h6>Ошибка</h6>
      </header>
      <p class="w3-container" >{{store.state.error}}</p>
    </div>
    <div v-if="store.state.message !== ''" class="w3-modal-content w3-display-container w3-display-bottomright"
         style="display: block; z-index:100; min-width: 15%; max-width: 200px">
      <header class="w3-container w3-teal">
        <span v-on:click="store.commit('msg', '')"
              class="w3-button w3-display-topright">&times;</span>
        <h6>Ошибка</h6>
      </header>
      <p class="w3-container w3-white" >{{store.state.message}}</p>
    </div>
  </div>
</template>

<script>
import 'w3-css'
import {axios} from "@/_helpers";
import {auth} from "@/_helpers/auth";
import {store} from "@/_helpers";
import LoginModal from "@/components/modal/LoginModal";
import RegModal from "@/components/modal/RegModal";
import ChangeUserDataModal from "@/components/modal/ChangeUserDataModal";

export default {
  data() {
    return {
      admin: false,
      showLoginModal: false,
      showRegModal: false,
      showUserDataModal: false,
      auth: auth,
      store: store
    }
  },
  components: {
    'login-modal': LoginModal,
    'reg-modal': RegModal,
    'user-data': ChangeUserDataModal
  },
  name: "Navbar",
  methods: {
    logout() {
      axios.get('/auth/logout').finally(() => auth.update())
      this.$router.push('/')
      this.switchLoginModal()
    },
    switchAdmin() {
      this.admin = !this.admin
    },
    switchLoginModal() {
      auth.update()
      this.showLoginModal = !this.showLoginModal
    },
    switchRegModal() {
      this.showRegModal = !this.showRegModal
    },
    switchUserDataModal() {
      this.showUserDataModal = !this.showUserDataModal
    }
  }
}
</script>

<style scoped>

</style>