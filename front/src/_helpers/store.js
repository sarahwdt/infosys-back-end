import Vuex from 'vuex'
import Vue from "vue";

Vue.use(Vuex)

export const store =  new Vuex.Store({
    state:{
        user: '',
        error: '',
        message: ''
    },
    mutations: {
        change(state, user) {
            state.user = user
            console.log(user)
        },
        error(state, error){
            state.error = error
        },
        msg(state, message){
            state.message = message
        }
    }
});