<script setup lang='ts'>
import { ref, reactive, watch, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { Delete, Plus } from '@element-plus/icons-vue';
import VuePictureCropper, { cropper } from 'vue-picture-cropper';
import { provinceAndCityData, CodeToText } from 'element-china-area-data';
import { getUserB, editUserB } from '../../api/userapi';
import { habitualResidence2Selected, changeUploadComponent } from '../../fun';
import { uploadImgB } from '../../api/commonapi';
const user = reactive({
    id: '',
    name: '',
    avatar: '',
    message: '',
    fan: 0,
    follow: 0,
    sex: 2,
    habitualResidence: '',
    birthday: '',
});
const editData = reactive({
    id: '',
    name: '',
    avatar: '',
    message: '',
    sex: 2,
    habitualResidence: '',
    birthday: '',
});
const selectedHabitualResidence = ref([]);
const dialogA = ref(false);
const disabled = ref(false);
const files = ref([]);
const dialogB = ref(false);
const pic = ref('');
const uploadInput = ref<HTMLInputElement | null>(null) // 裁剪需要
const picName = ref('');
function submit() {
    editUserB(editData).then(res => {
        ElMessage.success((res as any).message);
        dialogA.value = false;
    });
}
function getUser() {
    getUserB().then(res => {
        Object.assign(user, res.data.user);
        Object.assign(editData, user);
        selectedHabitualResidence.value = habitualResidence2Selected(editData.habitualResidence) as never;
        if (editData.avatar !== '' && editData.avatar !== null) {
            files.value.unshift({} as never);
        }
    });
}
function handleRemove() {
    files.value = [];
    editData.avatar = '';
}
function writeImgToCropper(file: any) {
    const reader: FileReader = new FileReader()
    reader.readAsDataURL(file as Blob);
    reader.onload = (): void => {
        // 更新裁切弹窗的图片源
        pic.value = String(reader.result)
        // 显示裁切弹窗
        dialogB.value = true
        // 清空已选择的文件
        if (!uploadInput.value) return
        uploadInput.value.value = ''
    }
}
function beforeUpload(file: any) {
    picName.value = file.name;
    writeImgToCropper(file);
    return false;
}
function cropperImg() {
    cropper?.getFile({
        fileName: picName.value,
    }).then(res => { // res图片结果
        let fromData = new FormData();
        fromData.append('file', res as any);
        uploadImgB(fromData).then(ress => {
            editData.avatar = ress.data.url;
            files.value.unshift({} as never);
        });
    });
    dialogB.value = false;
}
function dialogAClose() {
    handleRemove();
    getUser();
}
watch(files, () => {
    if (dialogA.value) {
        changeUploadComponent(files);
    }
}, {deep: true});
watch(selectedHabitualResidence, () => {
    if (selectedHabitualResidence.value === undefined) {
        editData.habitualResidence = '';
        return;
    }
    editData.habitualResidence = CodeToText[selectedHabitualResidence.value[0]] + '/' + CodeToText[selectedHabitualResidence.value[1]];
});
onMounted(() => {
    getUser();
});
</script>

<template>
    <div>
        <!-- 头像、用户名、粉丝、关注 -->
        <div class="head-div">
            <!-- 头像 -->
            <div>
                <img class="avatar" :src="user.avatar === null ? '/defaultavatar.jpg' : user.avatar" />
            </div>
            <!-- 用户名、粉丝、关注 -->
            <div>
                <div class="user-name">{{ user.name }}</div>
                <div class="user-guestbook">{{ user.message === null ? '这个家伙很懒，什么都没留下。' : user.message }}</div>
                <div class="user-info">
                    粉丝：{{ user.fan }}
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    关注：{{ user.follow }}</div>
            </div>
        </div>
        <!-- 基本信息、用户性别、地区、生日 -->
        <div class="content-div">
            <!-- 头部 -->
            <div class="head">
                基本信息
                <a class="btn" @click="dialogA = true">编辑</a>
            </div>
            <!-- 内容 -->
            <div>
                <ul class="list">
                    <li v-if="user.sex === 0">性别：女</li>
                    <li v-if="user.sex === 1">性别：男</li>
                    <li v-if="user.sex === 2">性别：保密</li>
                    <li>地区：{{ user.habitualResidence === null ? '暂无' : user.habitualResidence }}</li>
                    <li>生日：{{ user.birthday === null ? '暂无' : user.birthday }}</li>
                </ul>
            </div>
        </div>
    </div>
    <el-dialog
        v-model="dialogA"
        title="编辑"
        @close="dialogAClose"
    >
        <el-form label-position="top" ref="formRef" :model="editData">
            <el-form-item label="头像" prop="avatar">
                <el-upload
                    action="#" 
                    list-type="picture-card"
                    v-model:file-list="files"
                    :before-upload="beforeUpload"
                >
                    <el-icon><Plus /></el-icon>
                    <template #file="{ file }">
                        <img class="el-upload-list__item-thumbnail" :src="editData.avatar" alt="" />
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
            </el-form-item>
            <el-form-item label="昵称（必填）" prop="name">
                <el-input v-model="editData.name" placeholder="昵称" clearable />
            </el-form-item>
            <el-form-item label="留言" prop="words">
                <el-input v-model="editData.message" placeholder="来个签名~" clearable />
            </el-form-item>
            <el-form-item label="性别（必选）" prop="sex">
                <el-radio-group v-model="editData.sex">
                    <el-radio :label="1">男</el-radio>
                    <el-radio :label="0">女</el-radio>
                    <el-radio :label="2">保密</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="地区" prop="district">
                <el-cascader
                    :options="provinceAndCityData"
                    v-model="selectedHabitualResidence"
                    placeholder="留下你的城市"
                    clearable
                >
                </el-cascader>
            </el-form-item>
            <el-form-item label="生日" prop="birthday">
                <el-date-picker
                    v-model="editData.birthday"
                    type="date"
                    placeholder="留个生日~"
                    value-format="YYYY-MM-DD"
                />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submit">提交</el-button>
                <el-button>清空</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
    <el-dialog v-model="dialogB" title="编辑头像">
        <template #footer>
            <el-button @click="dialogB = false">取消</el-button>
            <el-button type="primary" @click="cropperImg">裁切</el-button>
        </template>
        <VuePictureCropper
            :boxStyle="{
                width: '100%',
                height: '100%',
                backgroundColor: '#f8f8f8',
                margin: 'auto',
            }"
            :img="pic"
            :options="{
                viewMode: 1,
                dragMode: 'move',
                aspectRatio: 1,
                cropBoxResizable: false,
            }"
            :presetMode="{
                mode: 'fixedSize',
                width: 150,
                height: 150,
            }"
        />
    </el-dialog>
</template>

<style scoped>
.btn {
    font-size: 14px;
    text-decoration: none;
    color: #000;
    font-weight: 400;
    display: block;
    position: absolute;
    right: 10px;
    cursor: pointer;
}
.btn:hover {
    color: #409eff;
}
.list {
    list-style: none;
    padding: 0;
    margin-left: 15px;
}
.list li {
    font-size: 14px;
    margin-bottom: 32px;
    line-height: 24px;
}
.head-div {
    margin-bottom: 15px;
    padding: 40px 40px;
    background-color: #fff;
    margin-left: 15px;
    border-bottom: 1px solid #dcdfe6;
    border-left: 1px solid #dcdfe6;
}
.content-div {
    background-color: #fff;
    margin-left: 15px;
    padding-bottom: 10px;
    border: 1px solid #dcdfe6;
}
.content-div .head {
    height: 48px;
    line-height: 48px;
    font-size: 18px;
    font-weight: 600;
    color: #2e2e2e;
    padding-left: 15px;
    border-bottom: 1px solid #f0f0f2;
    position: relative;
    display: flex;
}
.temp {
    width: 10px;
}
.user-info {

}
.user-guestbook {
    font-size: 14px;
    font-weight: 400;
    color: #555666;
    margin-bottom: 8px;
}
.user-name {
    /* font-weight: 500; */
    /* line-height: 24px; */
    font-size: 20px;
    margin-bottom: 8px;
}
.head-div {
    display: flex;
}
.avatar {
    width: 93px;
    height: 93px;
    border-radius: 50%;
    margin-right: 16px;
}
</style>