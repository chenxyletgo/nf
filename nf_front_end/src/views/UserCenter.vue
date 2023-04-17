<script setup lang='ts'>
import { ref, reactive } from 'vue';
import {
  Document,
  Menu as IconMenu,
  ChatDotRound,
  Lock,
  Setting,
} from '@element-plus/icons-vue';

</script>

<template>
    <div class="index-body">
        <div class="menu">
            <el-menu
                default-active="1"
                class="el-menu-vertical-demo"
                router
            >
                <el-menu-item index="info">
                    <el-icon><document /></el-icon>
                    <span>个人信息</span>
                </el-menu-item>
                <el-menu-item index="mesg">
                    <el-icon><chat-dot-round /></el-icon>
                    <span>消息中心</span>
                </el-menu-item>
                <el-sub-menu index="maga">
                    <template #title>
                        <el-icon><icon-menu /></el-icon>
                        <span>管理模块</span>
                    </template>
                        <el-menu-item index="atce" >文章管理</el-menu-item>

                        <el-menu-item index="cmet">评论管理</el-menu-item>

                </el-sub-menu>
                <el-menu-item index="acnt">
                    <el-icon><lock /></el-icon>
                    <span>账号中心</span>
                </el-menu-item>
                <el-menu-item index="stng">
                    <el-icon><setting /></el-icon>
                    <span>设置</span>
                </el-menu-item>
            </el-menu>
        </div>
        <div class="display-div">
            <router-view></router-view>
        </div>
    </div>
</template>

<style scoped>
.index-body {
    width: 1160px;
    margin: 0 auto;
    display: flex;
    margin-top: 15px;
}
.menu {
    width: 150px;
}
.display-div {
    width: 900px;
    /* margin: 40px 40px; */
}
</style>