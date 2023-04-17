<script setup lang='ts'>
import { ElMessage } from 'element-plus';
import { ref, reactive, onMounted } from 'vue';
import { getAllUserB, getAllArticleB, addLikeAB, addCommentB } from '../api/testapi';
const users = ref();
const articles = ref();
const likeAData = reactive({
    userId: '',
    articleId: '',
});
const CommentData = reactive({
    userId: '',
    articleId: '',
    words: '',
});
function sumitLikeA() {
    addLikeAB(likeAData).then(res => {
        ElMessage.success((res as any).message);
    });
}
function sumitComment() {
    addCommentB(CommentData).then(res => {
        ElMessage.success((res as any).message);
    });
}
function getAllUser() {
    getAllUserB().then(res => {
        users.value = res.data.list;
    });
}
function getAllArticle() {
    getAllArticleB().then(res => {
        articles.value = res.data.list;
    });
}
onMounted(() => {
    getAllUser();
    getAllArticle();
});
</script>

<template>
    <div>
        <h1>添加喜欢</h1>
        <el-form 
            :model="likeAData"
            :inline="true"
            class="demo-form-inline"
            status-icon
        >
            <el-form-item prop="userId">
                <div style="width: 90px;">
                    <el-select v-model="likeAData.userId" clearable placeholder="用户">
                        <el-option
                            v-for="item in users"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                        </el-option>
                    </el-select>
                </div>
            </el-form-item>  
            <el-form-item prop="articleId">
                <div style="width: 90px;">
                    <el-select v-model="likeAData.articleId" clearable placeholder="文章">
                        <el-option
                            v-for="item in articles"
                            :key="item.id"
                            :label="item.title"
                            :value="item.id">
                        </el-option>
                    </el-select>
                </div>
            </el-form-item>  
            <el-form-item>
                <div style="margin-left: 102px;">
                    <div style="width: 100px; display: flex;">
                        <el-button type="primary" @click="sumitLikeA">提交</el-button>
                        
                    </div>
                </div>
            </el-form-item>
        </el-form>

        <h1>添加一级评论</h1>
        <el-form 
            :model="CommentData"
            :inline="true"
            class="demo-form-inline"
            status-icon
        >
            <el-form-item prop="userId">
                <div style="width: 90px;">
                    <el-select v-model="CommentData.userId" clearable placeholder="用户">
                        <el-option
                            v-for="item in users"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                        </el-option>
                    </el-select>
                </div>
            </el-form-item>  
            <el-form-item prop="articleId">
                <div style="width: 90px;">
                    <el-select v-model="CommentData.articleId" clearable placeholder="文章">
                        <el-option
                            v-for="item in articles"
                            :key="item.id"
                            :label="item.title"
                            :value="item.id">
                        </el-option>
                    </el-select>
                </div>
            </el-form-item>  
            <el-form-item prop="words">
                <textarea v-model="CommentData.words">
                    评论
                </textarea>
            </el-form-item> 
            <el-form-item>
                <div style="margin-left: 102px;">
                    <div style="width: 100px; display: flex;">
                        <el-button type="primary" @click="sumitComment">提交</el-button>
                        
                    </div>
                </div>
            </el-form-item>
        </el-form>
    </div>
</template>

<style scoped>

</style>