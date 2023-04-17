import cookie from 'js-cookie';
import { TextToCode } from 'element-china-area-data';

// 判断当前是否是登录状态
function isLogin(): boolean {
    if (cookie.get('nf_token')) return true;
    return false;
}

// 判断邮箱格式是否合法
const emailReg = /^[a-zA-Z0-9]+([-_.][A-Za-zd]+)*@([a-zA-Z0-9]+[-.])+[A-Za-zd]{2,5}$/;
function checkEmail(email: string): boolean {
    if (emailReg.test(email)) return true;
    return false;
}

function firstCatesAddOther(firstCates: any) {
    firstCates.value.unshift({
        id: '',
        name: '推荐',
        pathName: 'recommend',
    });
    firstCates.value.push({
        id: '',
        name: '其他',
        pathName: 'other',
    });
}

function treeCatesAddOther(treeCates: any) {
    for (let i = 0; i < treeCates.value.length; i++) {
        if (treeCates.value[i].children != null && treeCates.value[i].children.length !== 0) {
            treeCates.value[i].children.push({
                id: '',
                name: '其他',
                children: null,
            });
        }
    }
}

function secondCatesAddOther(secondCates: any) {
    secondCates.value.unshift({
        id: '',
        name: '全部',
    });
}

function changeUploadComponent(files: any) {
    let doc = document.getElementsByClassName('el-upload--picture-card')[0] as HTMLElement;
    if (files.value?.length === 1) {
        doc.style.visibility = 'hidden';
    } else {
        doc.style.removeProperty('visibility');
    }
}

function habitualResidence2Selected(habitualResidence: string) {
    if (habitualResidence === null) return;
    let arr = habitualResidence.split('/');
    let result = [];
    result[0] = TextToCode[arr[0]].code;
    result[1] = TextToCode[arr[0]][arr[1]].code;
    return result;
}

export { 
    isLogin, 
    checkEmail, 
    firstCatesAddOther,
    secondCatesAddOther, 
    changeUploadComponent,
    treeCatesAddOther,
    habitualResidence2Selected
};