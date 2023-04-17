<script setup lang="ts">
import { onMounted } from 'vue';
import { user } from './store';
import { getUserStateB } from './api/userapi';
import Menu from './components/Menu.vue';
function init() {
    getUserStateB().then(res => {
        if ((res as any).code === 20000) {
            user.name = res.data.user.name;
            user.avatar = res.data.user.avatar;
        }
    });
}
onMounted(() => {
    init();
});
</script>

<template>
    <div>
        <Menu></Menu>
    </div>
    <div>
        <router-view v-slot="{ Component }">
            <keep-alive>
                <component :is="Component" v-if="$route.meta.keepAlive" />             
            </keep-alive>
            <component :is="Component" v-if="!$route.meta.keepAlive" />
        </router-view>
    </div>
</template>

<style scoped>
</style>
