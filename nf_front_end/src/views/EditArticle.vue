<script setup lang='ts'>
import { ref, reactive, watch, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { Delete, Plus } from '@element-plus/icons-vue';
import { changeUploadComponent, treeCatesAddOther } from '../fun';
import { uploadImgUrl, uploadImgB } from '../api/commonapi';
import { getTreeCatesB } from '../api/categoryapi';
import { getArticleB, postArticleB } from '../api/articleapi';
import { ElMessage } from 'element-plus';
const route = useRoute();
const router = useRouter();
const data = reactive({
    id: '',
    title: '',
    firstCateId: '',
    secondCateId: '',
    cover: '',
    text: '',
    state: -1,
});
const categories = ref();
const selectedCates = ref([]);
const files = ref([]);
const disabled = ref(false);
function handleSuccess(res: any) {
    data.cover = res.data.url;
}
function handleRemove() {
    files.value = [];
    data.cover = '';
}
function uploadImg(event: any, insertImge: any, files: any) {
    let imgUrl;
    let tempData = new FormData();
    tempData.append('file', files[0]);
    uploadImgB(tempData).then(res => {
        imgUrl = res.data.url;
        insertImge({
            url: imgUrl,
        });
    });
}
function getCategories() {
    getTreeCatesB().then(res => {
        categories.value = res.data.list;
        treeCatesAddOther(categories);
    });
}
function getArticleId() {
    if (route.params && route.params.id) data.id = route.params.id as string;
    else data.id = '';
}
function getArticle() {
    getArticleB(data.id, -1).then(res => {
        Object.assign(data, res.data.article);
        if (data.cover !== null) files.value?.unshift({} as never);
        changeUploadComponent(files);
        if (data.firstCateId === null) selectedCates.value.push('' as never);
        else selectedCates.value.push(data.firstCateId as never);
        if (data.secondCateId === null) selectedCates.value.push('' as never);
        else selectedCates.value.push(data.secondCateId as never);
    }).catch(err => { router.push('/editarticle') })
}
function post() {
    postArticleB(data, 0).then(res => {
        ElMessage.success((res as any).message);
    });
}
function save() {
    postArticleB(data, 1).then(res => {
        ElMessage.success((res as any).message);
    });
}
function resetData() {
    data.id = '',
    data.title = '',
    data.firstCateId = '',
    data.secondCateId = '',
    data.cover = '',
    data.text = '',
    data.state = -1;
    selectedCates.value = [];
}
watch(files, () => {
    changeUploadComponent(files);
});
watch(selectedCates, () => {
    if (selectedCates.value === null) {
        data.firstCateId = '';
        data.secondCateId = '';
        return;
    }
    data.firstCateId = selectedCates.value[0];
    data.secondCateId = selectedCates.value[1];
})
watch(route, () => {
    getCategories();
    getArticleId();
    if (data.id !== '') getArticle();
    else resetData();
})
onMounted(() => {
    getCategories();
    getArticleId();
    if (data.id !== '') getArticle();
    else resetData();
});
</script>

<template>
    <div class="div-1">
        <div class="div-2">
            <el-form
                label-position="top"
                size="large"
                ref="formRef"
                status-icon
            >
                <el-form-item label="标题（必填）">
                    <el-input v-model="data.title" placeholder="请输入文章题目" clearable />
                </el-form-item>
                <el-form-item label="类型（非必选）">
                    <el-cascader 
                        v-model="selectedCates" 
                        :options="categories"
                        :props="{
                            children: 'children',
                            label: 'name',
                            value: 'id'
                        }"
                        placeholder="请选择文章类型" 
                        clearable />
                </el-form-item>
                <el-form-item label="封面（非必选）">
                    <el-upload
                        ref="uploadRef" 
                        :on-success="handleSuccess" 
                        :action="uploadImgUrl()" 
                        list-type="picture-card"
                        v-model:file-list="files"
                    >
                        <el-icon><Plus /></el-icon>
                        <template #file="{ file }">
                            <img class="el-upload-list__item-thumbnail" :src="data.cover" alt="" />
                            <span class="el-upload-list__item-actions">
                                <span
                                    v-if="!disabled"
                                    class="el-upload-list__item-delete"
                                    @click="handleRemove"
                                >
                                    <el-icon><Delete /></el-icon>
                                </span>
                            </span>
                        </template>
                    </el-upload>
                    <div class="div-3">
                        <el-button @click="post" type="primary" round>&nbsp;&nbsp;发布&nbsp;&nbsp;</el-button>
                        <!-- <el-button type="success" round>&nbsp;&nbsp;预览&nbsp;&nbsp;</el-button> -->
                        <el-button @click="save" v-if="data.state === -1 || data.state === 0" type="warning" round>&nbsp;&nbsp;草稿&nbsp;&nbsp;</el-button>
                        <el-button type="warning" round>&nbsp;&nbsp;清空&nbsp;&nbsp;</el-button>
                    </div>
                </el-form-item>
                <el-form-item label="正文（必填）" class="div-98">
                </el-form-item>
            </el-form>
        </div>
        <div>
            <v-md-editor 
                v-model="data.text" 
                height="800px"
                :disabled-menus="[]"
                @upload-image="uploadImg">
            </v-md-editor>
        </div>
    </div>
</template>

<style scoped>
.div-98 {
    margin-bottom: 0px;
}
.div-3 {
    position: absolute;
    right: 0px;
    bottom: 5px;
    width: 400px;
    height: 45px;
    display: flex;
    justify-content: flex-end;
}
.div-1 {
    width: 1160px;
    margin: 0 auto;
    margin-top: 10px;
    margin-bottom: 50px;
}
.div-2 {
    margin-top: 10px;
    margin-left: 5px;
}
</style>