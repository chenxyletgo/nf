<script setup lang='ts'>
import { ref, reactive, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { provinceAndCityData, CodeToText } from 'element-china-area-data';
import { User } from '../class';
import { getUserListA, setStateA, delUserA } from '../api/userapi';
// 查询相关数据
const sexs = [ { name: '女', value: 0 }, { name: '男', value: 1 }, { name: '保密', value: 2} ];
const states = [ { name: '正常', value: 0 }, { name: '禁言中', value:  1 }, { name: '封禁中', value: 2 }, { name: '管理员', value: 3 }];
const dates = ref(''); // 提交formData时将其中的值赋值给preDate、sufDate
const areas = ref(''); // 提交formData时将其中的值赋值给habitualResidence
const formData = reactive({
    name: '',
    sex: '', // 数值
    preDate: '',
    sufDate: '',
    habitualResidence: '',
    state: '', // 数值
});
// 查询结果相关数据
const users = ref<User[]>();
// 分页数据
const page = reactive({
    total: 0,
    size: 14,
    cur: 1,
});
function search() {
    changPage();
}
function reset() {
    dates.value = '';
    areas.value = '';
    formData.name = '';
    formData.sex = '';
    formData.preDate = '';
    formData.sufDate = '';
    formData.habitualResidence = '';
    formData.state = '';
    changPage();
}
function changPage(cur: number = 1) {
    collateFormData();
    getUserListA(formData, cur, page.size).then(res => {
        users.value = res.data.list;
        page.total = res.data.total;
    });
}
// 获得用户列表前整理formData数据
function collateFormData() {
    if (dates.value !== '') {
        formData.preDate = dates.value[0];
        formData.sufDate = dates.value[1];
    }
    if (areas.value !== '') {
        formData.habitualResidence = CodeToText[areas.value[0]] + '/' + CodeToText[areas.value[1]];
    }
}
// 修改用户状态
function setState(id: string, msg: number) {
    setStateA(id, msg).then(res => {
        ElMessage.success((res as any).message);
        changPage(page.cur);
    });
}
function delUser(id: string) {
    if (page.total % page.size === 1 && Math.floor(page.total / page.size) !== 0) {
        delUserA(id).then(res => {
            ElMessage.success((res as any).message);
            page.cur--;
            changPage(page.cur);
        });
    } else {
        delUserA(id).then(res => {
            ElMessage.success((res as any).message);
            changPage(page.cur);
        });
    }
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
                    placeholder="昵称">
                </el-input>
            </el-form-item>
            <el-form-item prop="sex">
                <div style="width: 90px;">
                    <el-select v-model="formData.sex" clearable placeholder="性别">
                        <el-option
                            v-for="item in sexs"
                            :key="item.value"
                            :label="item.name"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </div>
            </el-form-item>  
            <el-form-item prop="dates">
                <el-date-picker
                    v-model="dates"
                    type="datetimerange"
                    range-separator="To"
                    start-placeholder="起始日期-生日"
                    end-placeholder="截止日期-生日"
                    format="YYYY-MM-DD"
                    value-format="YYYY-MM-DD HH:mm:ss">
                </el-date-picker>
            </el-form-item>  
            <el-form-item prop="areas">
                <div style="width: 150px;">
                    <el-cascader
                        :options="provinceAndCityData"
                        v-model="areas"
                        placeholder="居住地"
                        clearable>
                    </el-cascader>
                </div>
            </el-form-item>  
            <el-form-item prop="state">
                <div style="width: 100px;">
                    <el-select v-model="formData.state" class="m-2" clearable placeholder="账号状态">
                        <el-option
                            v-for="item in states"
                            :key="item.value"
                            :label="item.name"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </div>
            </el-form-item>  
            <el-form-item>
                <div style="width: 100px; display: flex;">
                    <el-button type="primary" @click="search">搜索</el-button>
                    <el-button @click="reset">清空</el-button>
                </div>
            </el-form-item>
        </el-form>
    </div>
    <div class="div-2">
        <el-table
            :data="users"
            style="width: 100%"
        >
            <el-table-column prop="name" label="昵称" width="140" />
            <el-table-column prop="sex" label="性别" width="140">
                <template #default="scope">
                    <span v-if="scope.row.sex === 0">女</span>
                    <span v-if="scope.row.sex === 1">男</span>
                    <span v-if="scope.row.sex === 2">保密</span>
				</template>
            </el-table-column>
            <el-table-column prop="birthday" label="生日" width="140">
                <template #default="scope">
                    <span v-if="scope.row.birthday === null">暂无</span>
                    <span v-if="scope.row.birthday !== null">{{ scope.row.birthday.slice(0, 10) }}</span>
				</template>
            </el-table-column>
            <el-table-column prop="habitualResidence" label="居住地" width="140">
                <template #default="scope">
                    <span v-if="scope.row.habitualResidence === null">暂无</span>
                    <span v-if="scope.row.habitualResidence !== null">{{ scope.row.habitualResidence }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="state" label="账号状态"  width="140">
                <template #default="scope">
                    <span v-if="scope.row.state === 0">正常</span>
                    <span v-if="scope.row.state === 1">禁言中</span>
                    <span v-if="scope.row.state === 2">封禁中</span>
                    <span v-if="scope.row.state === 3">管理员</span>
				</template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间"  width="200"/>
            <el-table-column prop="id" label="操作" fixed="right">
                <template #default="scope">
                    <div class="div-buts">
                        <el-button @click="setState(scope.row.id, 1)" :disabled="scope.row.state === 1" link type="primary">禁言</el-button>
                        <el-button @click="setState(scope.row.id, 2)" :disabled="scope.row.state === 2" link type="primary">封禁</el-button>
                        <el-button @click="setState(scope.row.id, 3)" :disabled="scope.row.state === 3" link type="primary">设为管理员</el-button>
                        <el-button @click="setState(scope.row.id, 0)" :disabled="scope.row.state === 0" link type="primary">恢复正常</el-button>
                        <el-button @click="delUser(scope.row.id)" link type="primary">删除</el-button>
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
.div-buts {
    display: flex;
}
</style>