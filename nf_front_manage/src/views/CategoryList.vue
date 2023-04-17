<script setup lang='ts'>
import { ref, reactive, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { Category} from '../class';
import { strIsEmpty } from '../fun';
import { getCateListA, getFirstCateListA, addCategoryA, delCategoryA, getCateByIdA, editCateByIdA } from '../api/categoryapi';

const categoryLevel = [ { name: '一级', value: 1 }, { name: '二级', value: 2 } ];
const formData = reactive({
    name: '',
    level: '',
});
const categories = ref<Category[]>();
const page = reactive({
    total: 0,
    size: 14,
    cur: 1,
});
const dialogA = ref(false);
const dialogB = ref(false);
const dialogC = ref(false);
const dialogD = ref(false);
const firstCategories = ref<Category[]>();
const firstFormData = reactive({
    name: '',
    pathName: '',
})
const secondFormData = reactive({
    name: '',
    fatherId: '',
});
const editFormData = reactive({
    id: '',
    name: '',
    pathName: '',
});
function search() {
    changPage();
}
function reset() {
    formData.name = '';
    formData.level = '';
    changPage();
}
function changPage(cur: number = 1) {
    getCateListA(formData, cur, page.size).then(res => {
        categories.value = res.data.list;
        page.total = res.data.total;
    });
}
function getFirstCateList() {
    getFirstCateListA().then(res => {
        firstCategories.value = res.data.list;
    });
}
function resetFirstCateList() {
    firstCategories.value = [];
    resetSecondFormData();
}
function resetFirstFormData() {
    firstFormData.name = '';
    firstFormData.pathName = '';
}
function resetSecondFormData() {
    secondFormData.name = '';
    secondFormData.fatherId = '';
}
function addCategory(data: any) {
    if (strIsEmpty(data.name)) {
        ElMessage.error('名称不能为空');
        return;
    }
    if ('pathName' in data) {
        if (strIsEmpty(data.pathName)) {
            ElMessage.error('路径名不能为空');
            return;
        }
    }
    if ('fatherId' in data) {
        if (strIsEmpty(data.fatherId)) {
            ElMessage.error('父级不能为空');
            return;
        }
    }
    addCategoryA(data).then(res => {
        ElMessage.success((res as any).message);
        changPage(page.cur);
    });
}
function delCategory(id: string) {
    if (page.total % page.size === 1 && Math.floor(page.total / page.size) !== 0) {
        delCategoryA(id).then(res => {
            ElMessage.success((res as any).message);
            page.cur--;
            changPage(page.cur);
        });
    } else {
        delCategoryA(id).then(res => {
            ElMessage.success((res as any).message);
            changPage(page.cur);
        });
    }
}
function preEditCategory(id: string) {
    getCateByIdA(id).then(res => {
        editFormData.id = res.data.category.id;
        editFormData.name = res.data.category.name;
        editFormData.pathName = res.data.category.pathName;
        dialogD.value = true;
    });
}
function editCategory() {
    if (editFormData.pathName === null) editFormData.pathName = '-1';
    editCateByIdA(editFormData).then(res => {
        ElMessage.success((res as any).message);
        dialogD.value = false;
        changPage(page.cur);
    });
}
function resetEditFromData() {
    editFormData.id = '';
    editFormData.name = '';
    editFormData.pathName = '';
}
onMounted(() => {
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
            <el-form-item prop="name">
                <el-input 
                    v-model="formData.name"
                    placeholder="名称">
                </el-input>
            </el-form-item>
            <el-form-item prop="level">
                <div style="width: 90px;">
                    <el-select v-model="formData.level" clearable placeholder="级别">
                        <el-option
                            v-for="item in categoryLevel"
                            :key="item.value"
                            :label="item.name"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </div>
            </el-form-item>
            <el-form-item>
                <div style="margin-left: 666px;">
                    <div style="width: 100px; display: flex;">
                        <el-button @click="dialogA = true">添加类别</el-button>
                        <el-button type="primary" @click="search">搜索</el-button>
                        <el-button @click="reset">清空</el-button>
                    </div>
                </div>
            </el-form-item>
        </el-form>
    </div>
    <div class="div-2">
        <el-table
            :data="categories"
            style="width: 100%"
        >
            <el-table-column prop="name" label="名称" width="230"></el-table-column>
            <el-table-column prop="pathName" label="路径名称" width="230">
                <template #default="scope">
                    <span>{{ scope.row.pathName === null ? '无' : scope.row.pathName }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="fatherId" label="级别" width="230">
                <template #default="scope">
                    <span>{{ scope.row.fatherId === '0' ? '一级' : '二级' }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间"  width="300"/>
            <el-table-column prop="id" label="操作" fixed="right">
                <template #default="scope">
                    <div class="div-buts">
                        <el-button @click="preEditCategory(scope.row.id)" link type="primary">修改</el-button>
                        <el-button @click="delCategory(scope.row.id)" link type="primary">删除</el-button>
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
        title="Tips"
        width="30%"
    >
        <span>分类分为一级和二级，请选择你要添加的种类。</span>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogB = true">创建一级分类</el-button>
                <el-button @click="dialogC = true">创建二级分类</el-button>
            </span>
        </template>
    </el-dialog>
    <el-dialog
        v-model="dialogB"
        title="创建一级分类"
        width="30%"
        @close="resetFirstFormData"
    >
        <el-form>
            <el-form-item>
                <el-input 
                    v-model="firstFormData.name"
                    placeholder="类别名（最多八个字符）">
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-input 
                    v-model="firstFormData.pathName"
                    placeholder="路径名（最多16个小写字母）">
                </el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="addCategory(firstFormData)">提交</el-button>
                <el-button @click="resetFirstFormData">清空</el-button>
            </span>
        </template>
    </el-dialog>
    <el-dialog
        v-model="dialogC"
        title="创建二级分类"
        width="30%"
        @open="getFirstCateList"
        @close="resetFirstCateList"
    >
        <el-form>
            <el-form-item>
                <el-input 
                    v-model="secondFormData.name"
                    placeholder="类别名（最多八个字符）">
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-select v-model="secondFormData.fatherId" clearable placeholder="所属分类">
                    <el-option
                        v-for="item in firstCategories"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="addCategory(secondFormData)">提交</el-button>
                <el-button @click="resetSecondFormData">清空</el-button>
            </span>
        </template>
    </el-dialog>
    <el-dialog
        v-model="dialogD"
        title="修改类别"
        width="30%"
        @close="resetEditFromData"
    >
        <el-form :data="editFormData">
            <el-form-item prop="name">
                <el-input 
                    v-model="editFormData.name"
                    placeholder="名称（最多八个字符）">
                </el-input>
            </el-form-item>
            <el-form-item v-if="editFormData.pathName !== null" prop="pathName">
                <el-input 
                    v-model="editFormData.pathName"
                    placeholder="路径名称（最多16个小写字母）">
                </el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="editCategory">提交</el-button>
                <el-button @click="resetEditFromData">清空</el-button>
            </span>
        </template>
    </el-dialog>

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
</style>