<template>
  <div class="w3-modal w3-container" style="display: block;">
    <div class="w3-modal-content">
      <div v-if="!loading">
        <header class="w3-container w3-xxlarge w3-teal"><h2>{{ title }} {{ id }}</h2></header>
        <span v-on:click="close" class="w3-button w3-display-topright">&times;</span>
        <form class="w3-container w3-padding-bottom" @submit.prevent="submit">
          <div v-for="field in fieldsLocal" :key="field.key">
            <label style="width: 100%"><b>{{ field.title }}</b>
              <div class="w3-panel w3-pale-red"
                   v-if="field.validateFunction && field.validateFunction(item[field.key]) !== undefined">
                {{ field.validateFunction(item[field.key]) }}
              </div>
              <div v-if="field.component" :is="field.component" :disabled="readOnly"
                   @input="setDataByKey(field.key, $event)"
                   v-model="item[field.key]" v-bind="field.props? field.props():[]"></div>
              <input class="w3-input" size="" style="width: 100%" type="text"
                     v-else :disabled="readOnly || field.disabled" v-model="item[field.key]"
                     :placeholder="item[field.key]?item[field.key]:''">
            </label>
          </div>
          <button v-if="!readOnly" :disabled="fieldsLocal.find(field => field.validateFunction!==undefined
          && field.validateFunction(item[field.key]))"
                  class="w3-button w3-block w3-teal w3-section w3-padding" type="submit">
            {{ item.id ? "Изменить" : "Создать" }}
          </button>
        </form>
      </div>
      <div v-else class="loader w3-display-middle"></div>
    </div>
  </div>
</template>

<script>
import {axios} from "@/_helpers";

export default {
  name: "CrudModal",
  data() {
    return {
      item: {},
      loading: false,
      fieldsLocal: this.fields
    }
  },
  props: {
    url: String,
    id: Number,
    readOnly: {
      type: Boolean,
      default: false
    },
    title: String,
    fields: Array
  },
  created() {
    if (this.id) this.get()
    this.fieldsLocal = this.fields
  },
  watch: {
    id: {
      handler() {
        if (this.id) this.get()
      }
    }
  },
  methods: {
    setDataByKey(key, data) {
      this.item[key] = data;
    },
    close() {
      this.$emit('close')
    },
    submit() {
      if (this.item.id !== undefined) this.put()
      else this.post()
    },
    get() {
      this.loading = true
      axios.get(this.url + '/' + this.id).then(response => {
        this.item = response.data
      }).finally(() => this.loading = false)
    },
    put() {
      this.loading = true
      let data = this.item
      delete data['createdBy']
      delete data['createdDate']
      delete data['lastModifiedBy']
      delete data['lastModifiedDate']

      axios.put(this.url + '/' + this.id,
          data, {
            headers: {'Content-Type': 'application/json'},
          }).then(() => this.close())
          .finally(() => this.loading = false)
    },
    post() {
      this.loading = true
      let data = this.item

      delete data['createdBy']
      delete data['createdDate']
      delete data['lastModifiedBy']
      delete data['lastModifiedDate']
      axios.post(this.url,
          data, {
            headers: {'Content-Type': 'application/json'},
          }).then(() => this.close())
          .finally(() => this.loading = false)
    }
  },
  computed: {
    valid: function () {
      for (let field in this.fieldsLocal)
        if (field.validateFunction && field.validateFunction(this.item[field.key]) !== undefined)
          return false
      return true
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