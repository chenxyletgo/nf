<script setup lang='ts'>
import { ref, reactive, onMounted } from 'vue';
import { Article } from '../class';
import { cateListAddOther } from '../fun';
import { getAticleListA, setLevelA, delArticleA } from '../api/articleapi';
import { getTreeListA } from '../api/categoryapi';
import { ElMessage } from 'element-plus';
const levels = [ { name: '默认', value: 0 }, { name: '推荐', value:  1 }, { name: '置顶', value: 2 } ];
const categories = ref(); // 搜索类别选择器数据
const singleData = { // 搜索类别选择器中数据解释
    children: 'children',
    label: 'name',
    value: 'id',
};
const selectedCates = ref(''); // 提交formData时将其中的值赋值给firstCategoryId、secondCategoryId
const dates = ref(''); // 提交formData时将其中的值赋值给preDate、sufDate
const formData = reactive({
    title: '',
    level: '', // 数值
    state: '2', // 数值
    preDate: '',
    sufDate: '',
    firstCategoryId: '',
    secondCategoryId: '',
});
const articles = ref<Article[]>();
const page = reactive({
    total: 0,
    size: 14,
    cur: 1,
});
function search() {
    changPage();
}
function reset() {
    dates.value = '';
    selectedCates.value = '';
    formData.title = '';
    formData.level = '';
    formData.preDate = '';
    formData.sufDate = '';
    formData.firstCategoryId = '';
    formData.secondCategoryId = '';
    changPage();
}
function getCates() {
    getTreeListA().then(res => {
        categories.value = res.data.list;
        cateListAddOther(categories);
    });
}
function changPage(cur: number = 1) {
    collateFormData();
    getAticleListA(formData, cur, page.size).then(res => {
        articles.value = res.data.list;
        page.total = res.data.total;
    });
}
function collateFormData() {
    if (dates.value !== '') {
        formData.preDate = dates.value[0];
        formData.sufDate = dates.value[1];
    }
    if (selectedCates.value !== '') {
        formData.firstCategoryId = selectedCates.value[0];
        if (selectedCates.value.length === 2) {
            formData.secondCategoryId = selectedCates.value[1];
        }
    }
}
function setLevel(id: string, msg: number) {
    setLevelA(id, msg).then(res => {
        ElMessage.success((res as any).message);
        changPage(page.cur);
    });
}
function delArticle(id: string) {
    if (page.total % page.size === 1 && Math.floor(page.total / page.size) !== 0) {
        delArticleA(id).then(res => {
            ElMessage.success((res as any).message);
            page.cur--;
            changPage(page.cur);
        });
    } else {
        delArticleA(id).then(res => {
            ElMessage.success((res as any).message);
            changPage(page.cur);
        });
    }
}
function goArticle(id: string) {
    window.open('http://localhost:7770/article/' + id);
}
onMounted(() => {
    getCates();
    changPage();
});
</script>

<template>
    <div class="div-1">
        <el-form 
            :model="formData"
            :inline="true"
            class="demo-form-inline"
            status-icon
        >
            <el-form-item prop="title">
                <el-input 
                    v-model="formData.title"
                    placeholder="标题">
                </el-input>
            </el-form-item>
            <el-form-item prop="areas">
                <div style="width: 150px;">
                    <el-cascader
                        v-model="selectedCates"
                        :options="categories"
                        :props="singleData"
                        placeholder="类别"
                        clearable>
                    </el-cascader>
                </div>
            </el-form-item>   
            <el-form-item prop="dates">
                <el-date-picker
                    v-model="dates"
                    type="datetimerange"
                    range-separator="To"
                    start-placeholder="起始日期-修改"
                    end-placeholder="截止日期-修改"
                    format="YYYY-MM-DD HH:mm:ss"
                    value-format="YYYY-MM-DD HH:mm:ss">
                </el-date-picker>
            </el-form-item>  
            <el-form-item prop="level">
                <div style="width: 120px;">
                    <el-select v-model="formData.level" clearable placeholder="文章级别">
                        <el-option
                            v-for="item in levels"
                            :key="item.value"
                            :label="item.name"
                            :value="item.value"
                            clearable>
                        </el-option>
                    </el-select>
                </div>
            </el-form-item> 
            <el-form-item>
                <div style="margin-left: 102px;">
                    <div style="width: 100px; display: flex;">
                        <el-button type="primary" @click="search">搜索</el-button>
                        <el-button @click="reset">清空</el-button>
                    </div>
                </div>
            </el-form-item>
        </el-form>
    </div>
    <div class="div-2">
        <el-table
            :data="articles"
            style="width: 100%"
        >
            <el-table-column :show-overflow-tooltip="true" prop="title" label="标题" width="160" />
            <el-table-column prop="user" label="发布者" width="120">
                <template #default="scope">
                    <span>{{ scope.row.user.name }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="watch" label="观看次数" width="120" />
            <el-table-column prop="firstCategory" label="一级类别"  width="120">
                <template #default="scope">
                    <span>{{ scope.row.firstCategory === null ? '其他' : scope.row.firstCategory.name }}</span>
				</template>
            </el-table-column>
            <el-table-column prop="secondCategory" label="二级类别"  width="120">
                <template #default="scope">
                    <span>{{ scope.row.secondCategory === null ? '其他' : scope.row.secondCategory.name }}</span>
				</template>
            </el-table-column>
            <el-table-column prop="level" label="级别"  width="120">
                <template #default="scope">
                    <span v-if="scope.row.level === '0'">默认</span>
                    <span v-if="scope.row.level === '1'">推荐</span>
                    <span v-if="scope.row.level === '2'">置顶</span>
                </template>
            </el-table-column>
            <el-table-column prop="modifyTime" label="修改时间"  width="200"/>
            <el-table-column prop="id" label="操作" fixed="right">
                <template #default="scope">
                    <div class="div-buts">
                        <el-button @click="setLevel(scope.row.id, 0)" :disabled="scope.row.level === '0'" link type="primary">默认</el-button>
                        <el-button @click="setLevel(scope.row.id, 1)" :disabled="scope.row.level === '1'" link type="primary">推荐</el-button>
                        <el-button @click="setLevel(scope.row.id, 2)" :disabled="scope.row.level === '2'" link type="primary">置顶</el-button>
                        <el-button link type="primary" @click="goArticle(scope.row.id)">文章链接</el-button>
                        <el-button @click="delArticle(scope.row.id)" link type="primary">删除</el-button>
                    </div>
				</template>
            </el-table-column>
        </el-table>
    </div>
    <div>
        <el-pagination class="page-div"  v-if="page.total > page.size"
            v-model:current-page="page.cur"
            v-model:page-size="page.size"
            layout="prev, pager, next, jumper"
            :total="page.total"
            :background="true"
            @current-change="changPage">
        </el-pagination>
    </div>
</template>

<style scoped>
.div-1 {
    margin-top: 15px;
}
.div-2 {
    height: 600px;
}
.page-div {
    justify-content: center;
    margin-top: 20px;
}
.div-buts {
    display: flex;
}
</style>