<template>
  <div>
    <navbar></navbar>
    <router-view></router-view>
  </div>
</template>

<script>
import Navbar from "@/components/fragments/Navbar";

export default {
  name: 'app',
  components: {
    'navbar': Navbar
  }
};
</script>