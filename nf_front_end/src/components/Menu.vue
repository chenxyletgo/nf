<script setup lang='ts'>
import { ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import cookie from 'js-cookie';
import { user } from '../store';
import { isLogin } from '../fun';
const route = useRoute();
const router = useRouter();
const activeIndex = ref('');
const dialogA = ref(false);
watch(route, () => {
    activeIndex.value = route.path;
})
function userOut() {
    cookie.remove('nf_token');
    location.reload();
}
</script>

<template>
    <div class="menu-body">
        <el-affix>
            <el-menu
                :default-active="activeIndex"
                class="el-menu-demo"
                mode="horizontal"
                background-color="#545c64"
                text-color="#fff"
                active-text-color="#ffd04b"
                router
            >
                <el-menu-item index="/home">首页</el-menu-item>
                <el-menu-item index="/search">搜索</el-menu-item>
                <el-menu-item index="/login" v-if="!isLogin()">登录</el-menu-item>
                <el-menu-item index="/register" v-if="!isLogin()">注册</el-menu-item>
                <el-sub-menu index="/user" class="user-info" v-if="isLogin()">
                    <template #title>
                        <img class="avatar" :src="user.avatar === '' || user.avatar === null ? '/defaultavatar.jpg' : user.avatar"/>
                        {{ user.name }}
                    </template>
                    <el-menu-item index="/usercenter">个人中心</el-menu-item>
                    <el-menu-item index="/editarticle">发帖</el-menu-item>
                    <el-menu-item @click="dialogA = true">登出</el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-affix>
    </div>
    <el-dialog v-model="dialogA" title="提示" width="30%" center>
        <span>确定要退出登录吗？</span>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogA = false">取消</el-button>
                <el-button type="primary" @click="userOut">确定</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<style scoped>
.el-menu-demo {
    padding-left: calc(50% - 580px);
}
.user-info {
    margin-left: 900px;
}
.avatar {
    width: 45px;
    border-radius: 50%;
    padding-right: 20px;
}
</style>