<script setup lang='ts'>
import { ref, reactive, watch } from 'vue';
import { getArticleListByTitleB } from '../api/articleapi';
import ArticleDiv from '../components/home/ArticleDiv.vue';
import SonCategory from '../components/home/SonCategory.vue';
import { Search } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
const sorts = [{name: '默认排序', value: 0}, {name: '发布时间排序', value: 1},{name: '观看数最多', value: 3},{name: '喜欢数最多', value: 3},{name: '评论数最多', value: 4},];
const search = ref();
const articleDivs = ref();
const page = reactive({
    total: 0,
    size: 3,
    cur: 1,
});
let tempSearch: string;
const pageWatch = ref(0);
function setTempSearch() {
    tempSearch = search.value;
}
function changPage(cur: number = 1) {
    getArticleListByTitleB(tempSearch, pageWatch.value,  cur, page.size).then(res => {
        articleDivs.value = res.data.list;
        page.total = res.data.total;
    });
}
function checkSearch(): boolean {
    if (search.value === null || search.value === undefined || search.value === '') {
        ElMessage.warning('输入不能为空');
        return false;
    }
    return true;
}
function searchFun() {
    if (!checkSearch()) return;
    setTempSearch();
    changPage();
}
watch(pageWatch, () => {
    changPage();
});
</script>

<template>
    <div class="div-1">
        <div>
            <el-form
                :inline="true"
                class="demo-form-inline"
                status-icon>
                <el-form-item prop="title">
                    <el-input 
                        v-model="search"
                        placeholder="标题"
                        :prefix-icon="Search">
                    </el-input>
                </el-form-item>
                <el-form-item prop="title">
                    <el-button @click="searchFun" type="primary">搜索</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div>
            <div class="categories-body">
                <a class="on">
                    <span>全部</span>
                </a>
                <el-select v-model="pageWatch" class="m-2" placeholder="Select" size="small">
                    <el-option
                    v-for="item in sorts"
                    :key="item.value"
                    :label="item.name"
                    :value="item.value"
                    />
                </el-select>
            </div>
        </div>
        <div>
            <ArticleDiv :data="item" v-for="item in articleDivs"></ArticleDiv>
        </div>
        <div>
            <el-pagination class="page-div"  v-if="page.total > page.size"
                    v-model:current-page="page.cur"
                    v-model:page-size="page.size"
                    layout="prev, pager, next, jumper"
                    :total="page.total"
                    :background="true"
                    @current-change="changPage"
                />
        </div>
    </div>
</template>

<style scoped>
.div-1 {
    width: 1120px;
    margin: 0 auto;
    margin-top: 10px;
}
.categories-body a {
    cursor: pointer;
}
.categories-body {
    margin-bottom: 22px;
    border-bottom: 1px solid #f4f5f7;
}
.categories-body a {
    display: inline-block;
    padding: 5px 10px 10px;
    margin-right: 20px;
    text-decoration: none;
    color: #000;
    font-size: 12px;
    transition: 0.2s;
}
.categories-body a:hover {
    color: #00AEEC;
}

.on {
    border-bottom: 1px solid #00AEEC;
}
.on span {
    color: #00AEEC;
}
</style>