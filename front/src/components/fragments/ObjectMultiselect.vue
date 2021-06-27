<template>
  <div style="width: 100%" :value="value" :disabled="disabled">
    <div v-if="!isModalOpen && selected.length === 0" class="w3-padding" v-on:click="modal">
      <span class=" w3-border w3-button w3-hover-none w3-round-xxlarge" style="margin-outside: 5px; margin-top: 3px">
        Пусто</span>
    </div>
    <div v-show="!isModalOpen" class="w3-padding"
         v-on:click="modal">
      <span v-for="(item, index) in selected" :key="index"
            class=" w3-border w3-button w3-hover-none w3-round-xxlarge" style="margin-outside: 5px; margin-top: 3px">
        {{ displayedValue(item) }}</span>
    </div>
    <div v-show="isModalOpen" style="border: 16px solid black; width: 100%"
         class="w3-padding w3-display-container w3-light-gray w3-border w3-border-black">
      <span v-on:click="modal" class="w3-opacity w3-button w3-display-topright">&times;</span>
      <span v-for="(item, index) in storedItems" :key="index">
      <span class="w3-button w3-border w3-round-xxlarge" style="margin-outside: 5px; margin-top: 3px"
            :class="{'w3-pale-red w3-border-red w3-hover-red':isIncluded(item), 'w3-pale-green w3-border-green w3-hover-green':!isIncluded(item)}"
            v-on:click="itemClick(item)" >
                <span style="margin: 0" class="w3-hover-none w3-circle ">{{ isIncluded(item) ? '-' : '+' }}</span>
        {{ displayedValue(item) }}
      </span>
        <span class="w3-margin-right">   </span>
      </span>
    </div>
  </div>
</template>

<script>
import {axios} from "@/_helpers";

export default {
  name: "ObjectMultiselect",
  data() {
    return {
      selected: this.value,
      isModalOpen: false,
      storedItems: []
    }
  },
  methods: {
    modal() {
      if (!this.disabled) {
        this.get()
        this.isModalOpen = !this.isModalOpen
      }
    },
    get() {
      axios.get(this.url).then(response => this.storedItems = response.data.content)
    },
    itemClick(item) {
      if (this.isIncluded(item))
        this.selected = this.selected.filter(value => value.id !== item.id)
      else this.selected.push(item)
    },
    isIncluded(item) {
      return this.selected.some(i => i.id === item.id)
    }
  },
  watch: {
    selected: {
      deep: true,
      handler() {
        this.$emit('input', this.selected)
      }
    }
  },
  props: {
    disabled: Boolean,
    url: String,
    displayedValue: {
      type: Function,
      default: (item) => item
    },
    value: {
      type: Array,
      default: () => []
    }
  }
}
</script>

<style scoped>

</style>