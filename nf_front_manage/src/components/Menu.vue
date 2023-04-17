<script setup lang='ts'>
import { User, Document, Collection, Setting } from '@element-plus/icons-vue';
import { curRoutePath } from '../store';
</script>

<template>
    <div class="div-1">
        <el-menu
            :default-active="curRoutePath"
            class="el-menu-vertical-demo"
            router
        >
            <el-sub-menu index="1">
                <template #title>
                    <el-icon><user /></el-icon>
                    <span>用户管理</span>
                </template>
                <el-menu-item index="userlist">用户列表</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="2">
                <template #title>
                    <el-icon><document /></el-icon>
                    <span>文章管理</span>
                </template>
                <el-menu-item index="articlelist">文章列表</el-menu-item>
                <el-menu-item index="articlereview">文章审核</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="3">
                <template #title>
                    <el-icon><collection /></el-icon>
                    <span>类别管理</span>
                </template>
                <el-menu-item index="categorylist">类别列表</el-menu-item>
                <el-menu-item index="categorytree">树型列表</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="4">
                <template #title>
                    <el-icon><collection /></el-icon>
                    <span>排行榜</span>
                </template>
                <el-menu-item index="">粉丝排行榜（扩展）</el-menu-item>
                <el-menu-item index="">文章排行榜（扩展）</el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="5">
                <template #title>
                    <el-icon><collection /></el-icon>
                    <span>测试模块</span>
                </template>
                <el-menu-item index="articletest">文章测试</el-menu-item>
            </el-sub-menu>
        </el-menu>
    </div>
</template>

<style scoped>
.div-1 {
    width: 200px;
}
</style>