<script setup lang='ts'>
import { ref, reactive, onMounted } from 'vue';
import { Article } from '../class';
import { cateListAddOther } from '../fun';
import { getAticleListA, setStateA } from '../api/articleapi';
import { getTreeListA } from '../api/categoryapi';
import { ElMessage } from 'element-plus';
import { fa } from 'element-plus/es/locale';
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
    state: '1', // 数值
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
const noPassData = reactive({
    id: '',
    msg: -1,
    reason: '',
});
const dialogA = ref(false);
const dialogB = ref(false);
const previewImg = ref();
function search() {
    changPage();
}
function reset() {
    dates.value = '';
    selectedCates.value = '';
    formData.title = '';
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
function setState(id: string, msg: number, reason?: string) {
    if (page.total % page.size === 1 && Math.floor(page.total / page.size) !== 0) {
        console.log(page.total % page.size === 1 && Math.floor(page.total / page.size) !== 0)
        setStateA(id, msg, reason).then(res => {
            ElMessage.success((res as any).message);
            page.cur--;
            changPage(page.cur);
            resetNoPassData();
            dialogA.value = false;
        });
    } else {
        setStateA(id, msg, reason).then(res => {
            ElMessage.success((res as any).message);
            changPage(page.cur);
            resetNoPassData();
            dialogA.value = false;
        });
    }
}
function preview(id: string) {
    window.open('/preview/' + id);
}
function noPass(id: string, msg: number) {
    noPassData.id = id;
    noPassData.msg = msg;
    dialogA.value = true;
}
function resetReason() {
    noPassData.reason = '';
}
function resetNoPassData() {
    noPassData.id = '';
    noPassData.msg = -1;
    noPassData.reason = '';
}
function previewImgFun(imgSrc: string) {
    previewImg.value = imgSrc;
    dialogB.value = true;
}
function closeDialogB() {
    previewImg.value = '';
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
            <el-form-item>
                <div style="margin-left: 254px;">
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
            <el-table-column :show-overflow-tooltip="true" prop="title" label="标题" width="200" />
            <el-table-column prop="user" label="发布者" width="180">
                <template #default="scope">
                    <span>{{ scope.row.user.name }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="firstCategory" label="一级类别"  width="180">
                <template #default="scope">
                    <span>{{ scope.row.firstCategory === null ? '其他' : scope.row.firstCategory.name }}</span>
				</template>
            </el-table-column>
            <el-table-column prop="secondCategory" label="二级类别"  width="180">
                <template #default="scope">
                    <span>{{ scope.row.secondCategory === null ? '其他' : scope.row.secondCategory.name }}</span>
				</template>
            </el-table-column>
            <el-table-column prop="modifyTime" label="修改时间"  width="250"/>
            <el-table-column prop="id" label="操作" fixed="right">
                <template #default="scope">
                    <div class="div-buts">
                        <el-button @click="setState(scope.row.id, 2)" link type="primary">通过</el-button>
                        <el-button @click="noPass(scope.row.id, 3)" link type="primary">打回</el-button>
                        <el-button @click="previewImgFun(scope.row.cover)" link type="primary" :disabled="scope.row.cover === null">封面预览</el-button>
                        <el-button @click="preview(scope.row.id)" link type="primary">文章预览</el-button>
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
    <el-dialog
        v-model="dialogA"
        title="打回原因"
        width="30%"
        @close="resetNoPassData"
    >
        <el-form :data="noPassData">
            <el-form-item prop="name">
                <el-input 
                    v-model="noPassData.reason"
                    placeholder="原因">
                </el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="setState(noPassData.id, noPassData.msg, noPassData.reason)">提交</el-button>
                <el-button @click="resetReason">清空</el-button>
            </span>
        </template>
    </el-dialog>
    <el-dialog v-model="dialogB" @close="closeDialogB">
        <img class="img-div" :src="previewImg" />
    </el-dialog>
</template>

<style scoped>
.img-div {
    max-width: 730px;
}
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