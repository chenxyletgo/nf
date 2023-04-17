<script setup lang='ts'>
import { ref, reactive } from 'vue';
import Menu from './Menu.vue';
</script>

<template>
    <div class="div-1">
        <div>
            <Menu></Menu>
        </div>
        <div>
            <router-view></router-view>
        </div>
    </div>
</template>

<style scoped>
.div-1 {
    display: flex;
}
</style>