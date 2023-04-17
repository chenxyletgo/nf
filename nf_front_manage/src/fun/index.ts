import { ElMessage } from 'element-plus';
import cookie from 'js-cookie';
import { getTreeListA } from '../api/categoryapi';

// 判断当前是否是登录状态
function isLogin(): boolean {
    if (cookie.get('nf_token_')) return true;
    return false;
}

// 判断邮箱格式是否合法
const emailReg = /^[a-zA-Z0-9]+([-_.][A-Za-zd]+)*@([a-zA-Z0-9]+[-.])+[A-Za-zd]{2,5}$/;
function checkEmail(email: string): boolean {
    if (emailReg.test(email)) return true;
    return false;
}

// 判断字符是否为空
function strIsEmpty(str: string): boolean {
    if (str === null || str === '' || str.trim() === '') return true;
    return false;
}

// 对获取到的类别列表添加其他元素
function cateListAddOther(categories: any) {
    for (let i = 0; i < categories.value.length; i++) {
        if (categories.value[i].children != null && categories.value[i].children.length !== 0) {
            categories.value[i].children.unshift({
                id: '',
                name: '全部',
                children: null,
            });
        }
    }
}

function likeArticle(id: string) {
    if (!isLogin()) {
        ElMessage.error('未登录');
        return;
    }
    
}

export { isLogin, checkEmail, strIsEmpty, cateListAddOther };