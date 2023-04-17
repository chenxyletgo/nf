<script setup lang='ts'>
import { ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import cookie from 'js-cookie';
import { ElMessage } from 'element-plus';
import type { FormInstance } from 'element-plus';
import { IPaddr, user } from '../store';
import { loginB, getCheckCodeUrlB } from '../api/userapi';
const router = useRouter();
const formRef = ref<FormInstance>();
const checkCodeUrl = ref();
const formData = reactive({
    email: '',
    password: '',
    checkCode: '',
});
function changeCheckCode() {
    checkCodeUrl.value = getCheckCodeUrlB();
}
function login(form: FormInstance | undefined) {
    if (!form) return;
    form.validate((valid) => {
        if (valid) { // valid代表表单内容是否合格
            loginB(formData).then(res => {
                ElMessage.success((res as any).message);
                cookie.set('nf_token', res.data.token, { domain: IPaddr });
                user.name = res.data.name;
                user.avatar = res.data.avatar;
                router.push('/home');
            });
        } else {
            ElMessage.error('输入不正确');
        }
    });
}
function reset(form: FormInstance | undefined) {
    if (!form) return;
    form.resetFields();
}
onMounted(() => {
    changeCheckCode();
});
const rules = reactive({
    email: [
        { 
            validator: (rule: any, value: any, callback: any) => {
                let emailReg = /^[a-zA-Z0-9]+([-_.][A-Za-zd]+)*@([a-zA-Z0-9]+[-.])+[A-Za-zd]{2,5}$/;
                if (value.indexOf(' ') !== -1) {
                    callback(new Error('输入不能有空格'));
                } else if (value.trim() === '') {
                    callback(new Error('邮箱是需要的'));
                } else if (!emailReg.test(value)) {
                    callback(new Error('邮箱格式不正确'));
                }
                callback();
            },
        }
    ],
    password: [
        { 
            validator: (rule: any, value: any, callback: any) => {
                let passReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$/;
                if (value.indexOf(' ') !== -1) {
                    callback(new Error('输入不能有空格'));
                } else if (value.trim() === '') {
                    callback(new Error('密码是需要的'));
                } else if (!passReg.test(value)) {
                    callback(new Error('密码长度：[8-16]，内容：[数字和字母的组合]'));
                }
                callback();
            },
        }
    ],
    checkCode: [
        { 
            validator: (rule: any, value: any, callback: any) => {
                let checkCodeReg = /^[0-9A-Za-z]{5}$/;
                if (value.indexOf(' ') !== -1) {
                    callback(new Error('输入不能有空格'));
                } else if (value.trim() === '') {
                    callback(new Error('验证码是需要的'));
                } else if (!checkCodeReg.test(value)) {
                    callback(new Error('验证码长度：[5]，内容：[数字或字母]'));
                }
                callback();
            },
        }
    ],
});
</script>

<template>
    <div class="div-2">
        <div class="div-1">
            <h3>登录</h3>
            <el-form 
                :model="formData" 
                ref="formRef" 
                :rules="rules" 
                status-icon
            >
                <el-form-item prop="email">
                    <el-input 
                        v-model="formData.email"
                        placeholder="邮箱"
                        autocomplete="off"
                        clearable 
                    />
                </el-form-item>
                <el-form-item prop="password">
                    <el-input
                        v-model="formData.password"
                        type="password"
                        placeholder="密码"
                        autocomplete="off"
                        show-password
                    />
                </el-form-item>
                <el-form-item prop="checkCode">
                    <div class="div-3">
                        <el-input
                            v-model="formData.checkCode"
                            placeholder="验证码"
                            autocomplete="off"
                            clearable
                        />
                    </div>
                    <img
                        class="codeImg"
                        @click="changeCheckCode"
                        :src="checkCodeUrl"
                    />
                </el-form-item>  
                <el-form-item>
                    <el-button type="primary" @click="login(formRef)">登录</el-button>
                    <el-button @click="reset(formRef)">清空</el-button>
                    <a href="#" class="forgetLink">忘记密码？</a>
                </el-form-item>
            </el-form>
        </div>
    </div>
    
</template>

<style scoped>
.forgetLink {
    display: block;
    position: absolute;
    right: 0px;
}
.div-1 {
    width: 300px;
    margin: 0 auto;
    text-align: center;
}
.div-2 {
    width: 400px;
    margin: 0 auto;
    margin-top: 120px;
    border: 1px solid #dcdfe6;
    background-color: white;
    border-radius: 20px;
}
.div-3 {
    width: 160px;
}
.codeImg {
    height: 32px;
    border-radius: 5px;
    margin-left: 10px;
    border: 1px solid #dcdfe6;
    cursor: pointer;
}

</style>