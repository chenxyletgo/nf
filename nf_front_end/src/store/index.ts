import { ref, reactive } from "vue";

// 服务器IP地址
const IPaddr = 'localhost';

const user = reactive({
    name: '',
    avatar: '',
});

const homePage = reactive({
    pathName: '',
    total: 0,
    size: 3,
    cur: 1,
});
const homePageWatch = reactive({
    secondCateId: '',
    sort: 0,
});

// 评论 state
export const flushedComm = ref(0); // 发送一级评论后告诉Article组件刷新
export const flushedCommPro = ref(0); // 发送二级评论后告诉Comments组件刷新
export const action = ref(0);
export const replyState = reactive({
    haveReply: false,
    commId: '', //当前Reply所在的位置,一级标签
    isCommPro: false,
    commProId: '', //二级标签
    ruserId: '',
    reCreate: false,
    newCreate: {
        commId: '',
        commProId: '',
        ruserId: '',
    }
});
export const reply = (clickCommId: string, ruserId: string, father: string = '') => {
    let commId = ''; // 一级
    let commProId = ''; // 二级
    if (father === '') { // 说明clickCommId是一级
        commId = clickCommId;
    } else { // 说明clickCommId是二级
        commId = father;
        commProId = clickCommId;
    }
    if (replyState.haveReply) { // 已经有reply了
        if (replyState.commId === commId) { // 在同一个一级评论里
            if (replyState.isCommPro) { // 当前的reply是个二级
                if (commProId === '') { // 点击的是个一级
                    // TODO reply不变 改变 ruserid
                    setReplyState(true, commId, false, '', ruserId);
                } else { // 点击的是个二级
                    if (commProId === replyState.commProId) { // 点击的同一个一级下的同一个二级
                        // TODO 关闭reply
                        replyState.haveReply = false;
                    } else { // 点击的同一个一级下的不二二级
                        // TODO reply不变 改变 ruserid
                        setReplyState(true, commId, true, commProId, ruserId);
                    }
                }
            } else {    // 当前的reply不是二级
                if (commProId === '') { // 点击的是个一级
                    // TODO 关闭reply
                    replyState.haveReply = false;
                } else { // 点击的是个二级
                    // TODO reply不变 改变 ruserid
                    setReplyState(true, commId, true, commProId, ruserId);
                }
            }
        action.value++;
        } else { // 不在同一个一级评论里
            // TODO 关闭当前的reply
            replyState.haveReply = false;
            action.value++;
            replyState.reCreate = true;
            replyState.newCreate.commId = commId;
            replyState.newCreate.commProId = commProId;
            replyState.newCreate.ruserId = ruserId;
            // TODO 创建 reply 设置 ruserid
            // if (commProId === '') {
            //     setBlackup(true, commId, false, '', ruserId);
            // } else {
            //     setBlackup(true, commId, true, commProId, ruserId);
            // }
            // balckupaction.value++;
        }
    } else {
        // TODO 创建 reply 设置 ruserid
        if (commProId === '') { // 当前点击的是一级
            setReplyState(true, commId, false, '', ruserId);
        } else { // 当前点击的是二级
            setReplyState(true, commId, true, commProId, ruserId);
        }
        action.value++;
    }
};
function setReplyState(haveReply: boolean, commId: string, isCommPro: boolean, commProId: string, ruserId: string) {
    replyState.haveReply = haveReply;
    replyState.commId = commId;
    replyState.isCommPro = isCommPro;
    replyState.commProId = commProId;
    replyState.ruserId = ruserId;    
}

export { IPaddr, user, homePage, homePageWatch };