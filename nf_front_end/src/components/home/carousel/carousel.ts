// 类型声明
interface DOMs {
    list: HTMLElement,
    titles: HTMLElement,
    indicator: HTMLElement,
}
//data
let curCarouselIndex = 0; // 当前位置
let timer: NodeJS.Timer; // 定时器
// 获取dom元素
function getDoms() {
    return {
        list: document.getElementsByClassName('carousel-list')[0] as HTMLElement,
        titles: document.getElementsByClassName('titles')[0] as HTMLElement,
        indicator: document.getElementsByClassName('indicator')[0] as HTMLElement,
    }
}
// 获取carouselConf信息
function carouselConf(doms: DOMs) {
    return {
        imgCount: doms.titles.children.length,
        imgWidth: doms.list.children[0].children[0].children[0].clientWidth,
        sufIndex: doms.titles.children.length - 1,
    }
}
// 初始化carousel
function initCarousel(doms: DOMs) {
    doms.list.style.left = '0px';
    doms.titles.children[0].classList.add('on');
    doms.indicator.children[0].children[0].classList.remove('indicator-item');
    doms.indicator.children[0].children[0].classList.add('on');
}
/**
 * title 向下 向上 移动
 * @param cur 当前的位置
 * @param sufIndex carousel数组最后元素的下标
 * @param doms carousel的DOM元素
 * @param pre false表示向下，true表示向上
 */
function titleNext(cur: number, sufIndex: number, doms: DOMs, pre: boolean) {
    let temp = doms.titles.children;
    temp[cur].classList.remove('on'); // 移除当前元素样式
    if (pre) { // 向上
        if (cur === 0) { // 到头部
            temp[sufIndex].classList.add('on'); // 给尾元素加样式
        } else { // 非头部
            temp[cur - 1].classList.add('on'); // 给上一个元素加样式
        }
    } else { // 向下
        if (cur === sufIndex) { // 到末尾
            temp[0].classList.add('on'); // 给头元素加样式
        } else { // 非末尾
            temp[cur + 1].classList.add('on'); // 给下一个元素加样式
        }
    }
}
/**
 * indicator 向下 向上 移动
 * @param cur 当前的位置
 * @param sufIndex carousel数组最后元素的下标
 * @param doms carousel的DOM元素
 * @param pre false表示向下，true表示向上
 */
function indicatorNext(cur: number, sufIndex: number, doms: DOMs, pre: boolean) {
    let temp = doms.indicator.children;
    temp[cur].children[0].classList.remove('on'); // 移除当前样式
    temp[cur].children[0].classList.add('indicator-item');
    if (pre) { // 向上
        if (cur === 0) { // 到头部
            temp[sufIndex].children[0].classList.remove('indicator-item'); // 给尾元素加样式
            temp[sufIndex].children[0].classList.add('on');
        } else { // 非头部
            temp[cur - 1].children[0].classList.remove('indicator-item'); // 给上一个元素加样式
            temp[cur - 1].children[0].classList.add('on');
        }
    } else { // 向下
        if (cur === sufIndex) { // 到尾部
            temp[0].children[0].classList.remove('indicator-item'); // 给头元素加样式
            temp[0].children[0].classList.add('on');
        } else { // 非尾部
            temp[cur + 1].children[0].classList.remove('indicator-item'); // 给下一个元素加样式
            temp[cur + 1].children[0].classList.add('on');
        }
    }
}
// 定时器
function setTimer(doms: DOMs, target: number) {
    let conf = carouselConf(doms);
    let count = target * conf.imgWidth;
    curCarouselIndex = target;
    let sufIndex = conf.sufIndex;
    timer = setInterval(() => {
        doms.list.style.left = '-' + (count + conf.imgWidth) + 'px'; // 向左移动
        titleNext(curCarouselIndex, sufIndex, doms, false);
        indicatorNext(curCarouselIndex, sufIndex, doms, false);
        count += conf.imgWidth;
        curCarouselIndex++;
        if (count === conf.imgWidth * conf.imgCount) {
            doms.list.style.left = '0px';
            curCarouselIndex = 0;
            count = 0;
        }
    }, 5000);
}
// carousel运动入口
function carouselFun(){
    let doms = getDoms(); // 获取dom元素
    initCarousel(doms); // 初始化carousel
    setTimer(doms, 0); // 创建定时器
}
// 点击事件
function changeImg(target: number) {
    if (target === curCarouselIndex) {
    } else {
        clearInterval(timer); // 销毁定时器
        let doms = getDoms();
        doms.list.style.left = '-' + (target * carouselConf(doms).imgWidth) + 'px';
        doms.titles.children[curCarouselIndex].classList.remove('on');
        doms.titles.children[target].classList.add('on');
        doms.indicator.children[curCarouselIndex].children[0].classList.remove('on');
        doms.indicator.children[curCarouselIndex].children[0].classList.add('indicator-item');
        doms.indicator.children[target].children[0].classList.remove('indicator-item');
        doms.indicator.children[target].children[0].classList.add('on');
        setTimer(doms, target); // 创建定时器
    }
}
export {
    carouselFun, changeImg ,timer,
}