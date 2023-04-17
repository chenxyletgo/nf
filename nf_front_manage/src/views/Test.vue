<script setup lang='ts'>
import { ElMessage } from 'element-plus';
import { ref, reactive, onMounted } from 'vue';
import { getTreeListA } from '../api/categoryapi';
import { addArticleA, getImgA } from '../api/testapi';
import { getUserListA } from '../api/userapi';
const states = [ { name: '草稿', value: 0 }, { name: '审核中', value:  1 }, { name: '审核通过', value: 2 }, { name: '审核未通过', value: 3 }];
const categories = ref();
const users = ref();
const selectedCates = ref();
const formData = reactive({
    userId: '',
    title: '',
    firstCategoryId: '',
    secondCategoryId: '',
    state: -1,
    text: '',
});
const singleData = {
    children: 'children',
    label: 'name',
    value: 'id',
};
function getCates() {
    getTreeListA().then(res => {
        categories.value = res.data.list;
        for (let i = 0; i < categories.value.length; i++) {
            if (categories.value[i].children != null && categories.value[i].children.length !== 0) {
                categories.value[i].children.push({
                    id: '',
                    name: '其他',
                    children: null,
                });
            }
        }
    });
}
function getUsers() {
    getUserListA({}, 1, 100).then(res => {
        users.value = res.data.list;
    });
}
function submit() {
    if (selectedCates.value !== undefined) {
        formData.firstCategoryId = selectedCates.value[0];
        if (selectedCates.value.length === 2) {
            formData.secondCategoryId = selectedCates.value[1];
        }
    }
    addArticleA(formData).then(res => {
        ElMessage.success((res as any).message);
    });
}
let str = '<a href="">@小城</a>评论了你的<a href="">文章</a>';
/**
 * 你的<a href="
 * ">文章</a>审核通过了。
 */
function getImg(url: string) {
    getImgA(url).then(res => {
        console.log(res);
    });
}
onMounted(() => {
    getImg('https://vip-self-001.oss-cn-hangzhou.aliyuncs.com/2023/04/04/HfnseGaYTg.png');
    getUsers();
    getCates();
})

</script>

<template>
    <div>
        <v-md-editor :model-value="'[@小华](http://baidu.com)评论了你的[文章](http://baidu.com)'" mode="preview"></v-md-editor>
        <br />
        <span>{{ `<a href="">@小城</a>评论了你的<a href="">文章</a>` }}</span>
        <br />
        <a href="">@小城</a>评论了你的<a href="">文章</a>
        <br />
        你的<a href="">文章</a>通过审核
        <br />
        你的<a href="">文章</a>未通过审核，原因：
        <br />
        <span v-html="str"></span>
    </div>
    <div>
        <el-form>
            <el-form-item>
                <el-select v-model="formData.userId" clearable placeholder="用户">
                    <el-option
                        v-for="item in users"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-input 
                    v-model="formData.title"
                    placeholder="标题">
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-cascader
                    v-model="selectedCates"
                    :options="categories"
                    :props="singleData">
                </el-cascader>
            </el-form-item>
            <el-form-item>
                <el-select v-model="formData.state" clearable placeholder="状态">
                    <el-option
                        v-for="item in states"
                        :key="item.value"
                        :label="item.name"
                        :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <textarea v-model="formData.text">
                    正文
                </textarea>
            </el-form-item>
            <el-form-item>
                <el-button @click="submit">提交</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<style scoped>

</style>