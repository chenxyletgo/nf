import{d as x,r as y,o as N,a as V,b as R,c as a,e as c,f as i,F as v,g,t as I,u as l,_ as B,h as p,n as q,i as r,p as W,j as A,w as z,k as b,l as P,m as $,q as M,s as G,v as d,x as J,E as K,y as Q}from"./index-7043f5d2.js";import{E as X}from"./el-select-790cb6c3.js";import"./el-input-ea2dabc9.js";import"./el-scrollbar-a3a91b86.js";import{g as Y,a as Z}from"./articleapi-65b59442.js";import{g as tt,a as et}from"./categoryapi-5135ff34.js";import{A as st}from"./ArticleDiv-210455b1.js";import"./likeaapi-e991004f.js";let m=0,E;function D(){return{list:document.getElementsByClassName("carousel-list")[0],titles:document.getElementsByClassName("titles")[0],indicator:document.getElementsByClassName("indicator")[0]}}function w(t){return{imgCount:t.titles.children.length,imgWidth:t.list.children[0].children[0].children[0].clientWidth,sufIndex:t.titles.children.length-1}}function nt(t){t.list.style.left="0px",t.titles.children[0].classList.add("on"),t.indicator.children[0].children[0].classList.remove("indicator-item"),t.indicator.children[0].children[0].classList.add("on")}function it(t,s,o,n){let e=o.titles.children;e[t].classList.remove("on"),n?t===0?e[s].classList.add("on"):e[t-1].classList.add("on"):t===s?e[0].classList.add("on"):e[t+1].classList.add("on")}function at(t,s,o,n){let e=o.indicator.children;e[t].children[0].classList.remove("on"),e[t].children[0].classList.add("indicator-item"),n?t===0?(e[s].children[0].classList.remove("indicator-item"),e[s].children[0].classList.add("on")):(e[t-1].children[0].classList.remove("indicator-item"),e[t-1].children[0].classList.add("on")):t===s?(e[0].children[0].classList.remove("indicator-item"),e[0].children[0].classList.add("on")):(e[t+1].children[0].classList.remove("indicator-item"),e[t+1].children[0].classList.add("on"))}function F(t,s){let o=w(t),n=s*o.imgWidth;m=s;let e=o.sufIndex;E=setInterval(()=>{t.list.style.left="-"+(n+o.imgWidth)+"px",it(m,e,t,!1),at(m,e,t,!1),n+=o.imgWidth,m++,n===o.imgWidth*o.imgCount&&(t.list.style.left="0px",m=0,n=0)},5e3)}function ot(){let t=D();nt(t),F(t,0)}function lt(t){if(t!==m){clearInterval(E);let s=D();s.list.style.left="-"+t*w(s).imgWidth+"px",s.titles.children[m].classList.remove("on"),s.titles.children[t].classList.add("on"),s.indicator.children[m].children[0].classList.remove("on"),s.indicator.children[m].children[0].classList.add("indicator-item"),s.indicator.children[t].children[0].classList.remove("indicator-item"),s.indicator.children[t].children[0].classList.add("on"),F(s,t)}}const ct={class:"carousel-body"},dt={class:"postion-div"},rt={class:"carousel-list"},ut=["href"],_t=["src"],mt={class:"titles",ref:"titlesRef"},ht=["href"],pt={class:"indicator"},vt=["onClick"],ft=x({__name:"Carousel",setup(t){const s=y();function o(){Y().then(n=>{s.value=n.data.list})}return N(()=>{o()}),V(()=>{s.value.length!==0&&ot()}),R(()=>{clearInterval(E)}),(n,e)=>(a(),c("div",ct,[i("div",dt,[i("ul",rt,[(a(!0),c(v,null,g(s.value,u=>(a(),c("li",null,[i("a",{href:"/article/"+u.id},[i("img",{src:u.cover},null,8,_t)],8,ut)]))),256))]),i("div",mt,[(a(!0),c(v,null,g(s.value,u=>(a(),c("a",{href:"/article/"+u.id},I(u.title),9,ht))),256))],512),i("ul",pt,[(a(!0),c(v,null,g(s.value,(u,L)=>(a(),c("li",null,[i("div",{class:"indicator-item",onClick:k=>l(lt)(L)},null,8,vt)]))),256))])])]))}});const gt=B(ft,[["__scopeId","data-v-b72fbf88"]]),Ct="/character.png",yt=t=>(W("data-v-8f0ded5b"),t=t(),A(),t),Lt={class:"categories-body"},kt=["onClick"],bt={class:"sort-body"},$t={class:"sort-btn min-font"},xt={class:"sort-btn-label"},Nt={key:0},It={key:1},Bt={key:2},Et={key:3},St={key:4},zt=yt(()=>i("img",{src:Ct},null,-1)),Pt={class:"sort-list"},Wt=x({__name:"SonCategory",props:{data:null},setup(t){function s(){let o=document.getElementsByClassName("sort-list")[0];document.getElementsByClassName("sort-btn-label")[0].addEventListener("click",e=>{o.style.display="block",e.stopPropagation()}),document.addEventListener("click",()=>{o.style.display="none"}),o.addEventListener("click",e=>{e.stopPropagation(),o.style.display="none"})}return N(()=>{s()}),(o,n)=>(a(),c(v,null,[i("div",Lt,[(a(!0),c(v,null,g(t.data,e=>(a(),c("a",{class:q(l(r).secondCateId===e.id?"on":""),onClick:u=>l(r).secondCateId=e.id},[i("span",null,I(e.name),1)],10,kt))),256))]),i("div",bt,[i("div",$t,[i("label",xt,[l(r).sort===0?(a(),c("span",Nt,"默认排序")):p("",!0),l(r).sort===1?(a(),c("span",It,"发布时间排序")):p("",!0),l(r).sort===2?(a(),c("span",Bt,"观看数最多")):p("",!0),l(r).sort===3?(a(),c("span",Et,"喜欢数最多")):p("",!0),l(r).sort===4?(a(),c("span",St,"评论数最多")):p("",!0),zt]),i("ul",Pt,[i("li",{onClick:n[0]||(n[0]=e=>l(r).sort=0)},"默认排序"),i("li",{onClick:n[1]||(n[1]=e=>l(r).sort=1)},"发布时间排序"),i("li",{onClick:n[2]||(n[2]=e=>l(r).sort=2)},"观看数最多"),i("li",{onClick:n[3]||(n[3]=e=>l(r).sort=3)},"喜欢数最多"),i("li",{onClick:n[4]||(n[4]=e=>l(r).sort=4)},"评论数最多")])])])],64))}});const At=B(Wt,[["__scopeId","data-v-8f0ded5b"]]),Dt=t=>(W("data-v-ce1b044c"),t=t(),A(),t),wt={class:"div-2"},Ft={class:"div-1"},Ht={class:"left-list"},Ut={class:"div-5"},jt={class:"div-3"},Ot={key:0},Tt=Dt(()=>i("div",{class:"div-4"},[i("label",null,"推荐文章")],-1)),Vt={key:1},Rt=x({__name:"Home",setup(t){const s=M(),o=y(),n=y([]),e=y();function u(){tt().then(f=>{o.value=f.data.list,G(o)})}function L(f){et(f).then(_=>{n.value=_.data.list,Q(n)})}function k(){s.params&&s.params.category&&(d.pathName=s.params.category)}function S(){d.pathName!=="recommend"&&d.pathName!=="other"&&L(d.pathName),d.pathName==="other"&&(n.value=[{id:"",name:"全部"}])}function H(){r.secondCateId="",r.sort=0}function C(f=1){Z(Object.assign(d,r),f,d.size).then(_=>{e.value=_.data.list,d.total=_.data.total})}return z(s,()=>{n.value=[],k(),S(),H(),C()}),z(r,()=>{C()}),N(()=>{u(),k(),S(),C()}),(f,_)=>{const U=J("router-link"),j=K,O=X;return a(),c("div",wt,[i("div",Ft,[b(j,{offset:70},{default:P(()=>[i("div",Ht,[(a(!0),c(v,null,g(o.value,h=>(a(),$(U,{to:"/home/"+h.pathName},{default:P(()=>[i("span",null,I(h.name),1)]),_:2},1032,["to"]))),256))])]),_:1})]),i("div",Ut,[i("div",jt,[l(d).pathName==="recommend"?(a(),c("div",Ot,[b(gt),Tt])):p("",!0),l(d).pathName!=="recommend"?(a(),c("div",Vt,[b(At,{data:n.value},null,8,["data"])])):p("",!0),i("div",null,[(a(!0),c(v,null,g(e.value,(h,T)=>(a(),$(st,{key:new Date().getTime()+T,data:h},null,8,["data"]))),128))]),l(d).total>l(d).size?(a(),$(O,{key:2,class:"page-div","current-page":l(d).cur,"onUpdate:currentPage":_[0]||(_[0]=h=>l(d).cur=h),"page-size":l(d).size,"onUpdate:pageSize":_[1]||(_[1]=h=>l(d).size=h),layout:"prev, pager, next, jumper",total:l(d).total,background:!0,onCurrentChange:C},null,8,["current-page","page-size","total"])):p("",!0)])])])}}});const Zt=B(Rt,[["__scopeId","data-v-ce1b044c"]]);export{Zt as default};
