<script setup lang='ts'>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import type { FormInstance } from 'element-plus';
import { checkEmail } from '../fun';
import { registerB, sendEmailCodeB } from '../api/userapi';
const router = useRouter(); // 注册成功后跳转到登录页面
const formRef = ref<FormInstance>(); // 获取页面上的表单元素
const btnInfo = reactive({ // 发送验证码按钮状态数据
    show: true,
    count: 0,
    TIME_LENGTH: 60,
});
const formData = reactive({ // 表单数据
    name: '',
    email: '',
    password: '',
    confirm: '',
    emailCode: '',
});
function register(form: FormInstance | undefined) {
    if (!form) return;
    form.validate((valid) => {
        if (valid) { // valid代表表单内容是否合格
            registerB(formData).then(res => {
                ElMessage.success((res as any).message);
                router.push('/login');
            });
        } else {
            ElMessage.error('输入不正确');
        }
    });
}
function sendEmailCode() {
    if (!checkEmail(formData.email)) {
        ElMessage.error('邮箱格式不正确');
        return;
    }
    ElMessage.warning('邮箱推送服务器比较拥挤，请耐心等待。');
    sendEmailCodeB(formData.email).then(res => {
        ElMessage.success((res as any).message);
        changeBtn();
    });
}
function changeBtn() {
    btnInfo.show = false;
    btnInfo.count = btnInfo.TIME_LENGTH;
    let timer = setInterval(() => {
        if (btnInfo.count > 0 && btnInfo.count <= btnInfo.TIME_LENGTH) btnInfo.count--;
        else {
            btnInfo.show = true;
            clearInterval(timer);
        }   
    }, 1000);
}
function reset(form: FormInstance | undefined) {
    if (!form) return;
    form.resetFields();
}
const rules = reactive({
    name: [
        {
            validator: (rule: any, value: any, callback: any) => {
                if (value.indexOf(' ') !== -1) {
                    callback(new Error('输入不能有空格'));
                } else if (value.trim() === '') {
                    callback(new Error('昵称是需要的'));
                }
                callback();
            },
        }
    ],
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
    confirm: [
        {
            validator: (rule: any, value: any, callback: any) => {
                if (value.indexOf(' ') !== -1) {
                    callback(new Error('输入不能有空格'));
                } else if (value.trim() === '') {
                    callback(new Error('密码是需要的'));
                } else if (value !== formData.password) {
                    callback(new Error('密码不一致'));
                }
                callback();
            },
        }
    ],
    emailCode: [
        { 
            validator: (rule: any, value: any, callback: any) => {
                let checkCodeReg = /^[0-9]{6}$/;
                if (value.indexOf(' ') !== -1) {
                    callback(new Error('输入不能有空格'));
                } else if (value.trim() === '') {
                    callback(new Error('验证码是需要的'));
                } else if (!checkCodeReg.test(value)) {
                    callback(new Error('验证码长度：[6]，内容：[数字]'));
                }
                callback();
            },
        }
    ],
});
</script>

<template>
    <div class="div-3">
        <div class="div-1">
            <h3>注册</h3>
            <div class="form">
                <el-form
                    ref="formRef"
                    :model="formData"
                    status-icon
                    :rules="rules"
                    class="demo-ruleForm"
                >
                    <el-form-item prop="name">
                        <el-input 
                            v-model="formData.name"
                            placeholder="昵称"
                            autocomplete="off"
                            clearable
                        />
                    </el-form-item>
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
                            placeholder="密码"
                            autocomplete="off"
                            clearable
                            show-password
                        />
                    </el-form-item>
                    <el-form-item prop="confirm">
                        <el-input 
                            v-model="formData.confirm"
                            placeholder="再次输入密码"
                            autocomplete="off"
                            clearable
                            show-password
                        />
                    </el-form-item>
                    <el-form-item prop="emailCode">
                        <div class="div-2">
                            <el-input 
                                v-model="formData.emailCode"
                                placeholder="邮箱验证码"
                                autocomplete="off"
                                clearable
                            />
                        </div>
                        <el-button v-if="btnInfo.show" @click="sendEmailCode">发送验证码</el-button>
                        <el-button v-if="!btnInfo.show" disabled>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ btnInfo.count < 10 ? '&nbsp;&nbsp;' + btnInfo.count : btnInfo.count }}s&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</el-button>
                    </el-form-item>
                    <el-form-item>
                            <el-button @click="register(formRef)"  type="primary">注册</el-button>
                            <el-button @click="reset(formRef)">清空</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<style scoped>
.div-1 {
    width: 300px;
    margin: 0 auto;
    text-align: center;
}
.div-2 {
    width: 188px;
    margin-right: 10px;
}
.div-3 {
    width: 400px;
    margin: 0 auto;
    margin-top: 120px;
    border: 1px solid #dcdfe6;
    background-color: white;
    border-radius: 20px;
}
</style>