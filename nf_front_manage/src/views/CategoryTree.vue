<script setup lang='ts'>
import { ref, reactive, onMounted } from 'vue';
import { getTreeListA } from '../api/categoryapi';
const treeData = ref();
const singleData = {
    children: 'children',
    label: 'name',
};
function getTreeList() {
    getTreeListA().then(res => {
        treeData.value = res.data.list;
    });
}
onMounted(() => {
    getTreeList();
});
</script>

<template>
    <div class="div-1">
        <el-tree :data="treeData" :props="singleData"></el-tree>
    </div>
</template>

<style scoped>
.div-1 {
    margin-top: 40px;
}
</style>