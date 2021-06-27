import {axios} from "@/_helpers/axios";
import {store} from "@/_helpers/store";

export const auth = {
    update() {
        axios.get('/auth').then(response => {
            store.commit('change', response.data)
        }).catch(error => error && error.response && error.response.status === 401 ? store.commit('change', '')
            :console.error(error))
        return store.state.user === '';
    },
    hasRole(roleName) {
        const user = store.state.user
        return user && user.roles.find(role => role.name === roleName)
    },
    hasPrivilege(privilegeName) {
        const user = store.state.user
        return user !== '' && user.roles.find(role => role.privileges.find(privilege => privilege.name === privilegeName))
    }
}