<script setup lang='ts'>
import { ref, reactive } from 'vue';
import { Delete, Download, Plus, ZoomIn } from '@element-plus/icons-vue'
import type { UploadFile, UploadUserFile,UploadInstance  } from 'element-plus'
import { uploadImgUrl } from '../api/commonapi'
const uploadRef = ref<UploadInstance>();
const fileList = ref<UploadUserFile[]>([{
    name: '',
    url: 'https://ts1.cn.mm.bing.net/th/id/R-C.83854ca1c113f80f8912f66b44c6a506?rik=pbz0HvAE8A7V3A&riu=http%3a%2f%2fwww.gx8899.com%2fuploads%2fallimg%2f2018011510%2fza5bne1aicq.jpg&ehk=RQZv4LRjEA8iP1TDo9YQts8fGNKXYs9dEFCdZ2Y5oUw%3d&risl=&pid=ImgRaw&r=0',
}]);
const dialogImageUrl = ref('')
const dialogVisible = ref(false)
const disabled = ref(false)
const handleRemove = (file: UploadFile) => {
  fileList.value = [];
}
const handlePictureCardPreview = (file: UploadFile) => {
  dialogImageUrl.value = file.url!
  dialogVisible.value = true
}
const handleDownload = (file: UploadFile) => {
  console.log(file)
}
function look() {
    console.log(fileList.value)
}
function addFile() {
    fileList.value?.unshift({
        name: '',
        url: '',
    });
}
function upload() {
    uploadRef.value!.submit();
}
function success() {
    console.log('成功方法执行')
}
/**
 * title
 * cates
 * cover
 * text
 */
const article = reactive({
    title: '',
    firstCateId: '',
    secondCateId: '',
    cover: '',
    text: '',
});
</script>

<template>
    <el-button @click="addFile">添加文件</el-button>
    <el-button @click="look">查看fileLsit</el-button>
    <el-button @click="upload">上传</el-button>
    <el-upload :on-success="success" ref="uploadRef" :action="uploadImgUrl()" v-model:file-list="fileList" list-type="picture-card" :auto-upload="false">
        <el-icon><Plus /></el-icon>
        <template #file="{ file }">
        <div>
            <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
            <span class="el-upload-list__item-actions">
            <span
                class="el-upload-list__item-preview"
                @click="handlePictureCardPreview(file)"
            >
                <el-icon><zoom-in /></el-icon>
            </span>
            <span
                v-if="!disabled"
                class="el-upload-list__item-delete"
                @click="handleDownload(file)"
            >
                <el-icon><Download /></el-icon>
            </span>
            <span
                v-if="!disabled"
                class="el-upload-list__item-delete"
                @click="handleRemove(file)"
            >
                <el-icon><Delete /></el-icon>
            </span>
            </span>
        </div>
        </template>
    </el-upload>
    <el-dialog v-model="dialogVisible">
        <img w-full :src="dialogImageUrl" alt="Preview Image" />
    </el-dialog>
</template>

<style scoped>

</style>