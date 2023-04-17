import service from "./base";

const base = '/manservice/category';

export const getCateListA = (data: any, cur: number, size: number) => service({
    url: base + '/adm/getCateList/' + cur + '/' + size,
    method: 'post',
    data,
});

export const getFirstCateListA = () => service({
    url: base + '/adm/getFirstCateList',
    method: 'get',
});

export const addCategoryA = (data: any) => service({
    url: base + '/adm/addCategory',
    method: 'post',
    data,
});

export const delCategoryA = (id: string) => service({
    url: base + '/adm',
    method: 'delete',
    data: {
        id,
    }
});

export const getTreeListA = () => service({
    url: base + '/adm/getTreeList',
    method: 'get',
});

export const getCateByIdA = (id: string) => service({
    url: base + '/adm/' + id,
    method: 'get',
});

export const editCateByIdA = (data: any) => service({
    url: base + '/adm',
    method: 'put',
    data,
});