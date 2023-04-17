import { ref, reactive } from "vue";

// 服务器IP地址
const IPaddr = 'localhost';

const curRoutePath = ref('userlist');

export { IPaddr, curRoutePath };