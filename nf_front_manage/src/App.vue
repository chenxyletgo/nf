<script setup lang="ts">
import { watch } from 'vue';
import { useRoute } from 'vue-router';
import { curRoutePath } from './store';
const route = useRoute();
let pattern = /\/home\/[a-z]*/;
watch(route, () => {
    let path = route.path;
    if (pattern.test(path)) {
        curRoutePath.value = path.slice(6);
    }
});
</script>

<template>
    <router-view></router-view>
</template>

<style scoped>
</style>
