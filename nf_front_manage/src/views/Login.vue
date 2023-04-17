<script setup lang='ts'>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import cookie from 'js-cookie';
import { IPaddr } from '../store';
import { checkEmail } from '../fun';
import { login } from '../api/userapi';
const router = useRouter();
const formData = reactive({
    email: '',
    password: '',
});
// 提交数据
function submit() {
    if (!checkData()) {
        ElMessage.error('输入的数据不合法');
        return;
    }
    sendLoginReq();
}
// 清空数据
function reset() {
    formData.email = '';
    formData.password = '';
}
// 检查数据是否合法
function checkData(): boolean {
    // 检查邮箱
    let b = checkEmail(formData.email);
    if (!b) return false;
    // 检查密码
    if ('' === formData.password.trim()) return false;
    return true;
}
// 发送登录请求
function sendLoginReq() {
    login(formData).then((res: any)=> {
        ElMessage.success(res.message);
        // 保存token
        cookie.set('nf_token_', res.data.token, { domain: IPaddr });
        // 跳转到首页  
        router.push('/home');
    });
}
</script>

<template>
    <div class="div-1">
        <h1>登录</h1>
        <el-form 
            :model="formData"
            status-icon
        >
            <el-form-item prop="email">
                <el-input 
                    v-model="formData.email"
                    placeholder="邮箱"
                    clearable 
                />
            </el-form-item>
            <el-form-item prop="password">
                <el-input
                    v-model="formData.password"
                    type="password"
                    placeholder="密码"
                    show-password
                />
            </el-form-item>  
            <el-form-item>
                <el-button type="primary" @click="submit">登录</el-button>
                <el-button @click="reset">清空</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<style scoped>
.div-1 {
    width: 300px;
    margin: 0 auto;
    margin-top: 150px;
}
</style>