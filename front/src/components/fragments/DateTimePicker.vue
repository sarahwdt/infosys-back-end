<template>
  <input class="w3-input" type="datetime-local" v-bind:value="value" v-on:input="returnData($event.target.value)">
</template>

<script>

export default {
  name: "DateTimePicker",
  props: ['value'],
  methods:{
    returnData(data) {
      this.$emit('input', data);
    }
  },
}
</script>

<style scoped>

</style>