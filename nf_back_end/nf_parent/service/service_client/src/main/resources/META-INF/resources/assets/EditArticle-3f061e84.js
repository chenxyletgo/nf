import{d as q,z as G,r as m,w as C,o as K,e as k,f as u,k as t,l as o,q as L,A as S,W,X as y,x as X,c as h,u as g,Y,h as A,Z,C as I,m as $,G as B,$ as H,K as J,_ as P}from"./index-7043f5d2.js";import{E as Q,a as ee}from"./el-form-item-82fe4575.js";import{u as te,a as ae,E as le,b as se}from"./commonapi-9091e880.js";/* empty css                */import{E as oe}from"./el-input-ea2dabc9.js";import"./el-scrollbar-a3a91b86.js";import"./el-checkbox-99e20c18.js";import{b as ne}from"./categoryapi-5135ff34.js";import{b as ie,p as U}from"./articleapi-65b59442.js";import"./_initCloneObject-cf0c2ff8.js";const de={class:"div-1"},ue={class:"div-2"},re=["src"],ce={class:"el-upload-list__item-actions"},pe={class:"div-3"},me=q({__name:"EditArticle",setup(_e){const r=L(),w=S(),e=G({id:"",title:"",firstCateId:"",secondCateId:"",cover:"",text:"",state:-1}),_=m(),s=m([]),i=m([]),R=m(!1);function N(l){e.cover=l.data.url}function D(){i.value=[],e.cover=""}function F(l,a,f){let d,c=new FormData;c.append("file",f[0]),te(c).then(p=>{d=p.data.url,a({url:d})})}function b(){ne().then(l=>{_.value=l.data.list,W(_)})}function E(){r.params&&r.params.id?e.id=r.params.id:e.id=""}function V(){ie(e.id,-1).then(l=>{var a;Object.assign(e,l.data.article),e.cover!==null&&((a=i.value)==null||a.unshift({})),y(i),e.firstCateId===null?s.value.push(""):s.value.push(e.firstCateId),e.secondCateId===null?s.value.push(""):s.value.push(e.secondCateId)}).catch(l=>{w.push("/editarticle")})}function z(){U(e,0).then(l=>{B.success(l.message)})}function M(){U(e,1).then(l=>{B.success(l.message)})}function x(){e.id="",e.title="",e.firstCateId="",e.secondCateId="",e.cover="",e.text="",e.state=-1,s.value=[]}return C(i,()=>{y(i)}),C(s,()=>{if(s.value===null){e.firstCateId="",e.secondCateId="";return}e.firstCateId=s.value[0],e.secondCateId=s.value[1]}),C(r,()=>{b(),E(),e.id!==""?V():x()}),K(()=>{b(),E(),e.id!==""?V():x()}),(l,a)=>{const f=oe,d=ee,c=le,p=H,O=se,v=J,T=Q,j=X("v-md-editor");return h(),k("div",de,[u("div",ue,[t(T,{"label-position":"top",size:"large",ref:"formRef","status-icon":""},{default:o(()=>[t(d,{label:"标题（必填）"},{default:o(()=>[t(f,{modelValue:e.title,"onUpdate:modelValue":a[0]||(a[0]=n=>e.title=n),placeholder:"请输入文章题目",clearable:""},null,8,["modelValue"])]),_:1}),t(d,{label:"类型（非必选）"},{default:o(()=>[t(c,{modelValue:s.value,"onUpdate:modelValue":a[1]||(a[1]=n=>s.value=n),options:_.value,props:{children:"children",label:"name",value:"id"},placeholder:"请选择文章类型",clearable:""},null,8,["modelValue","options"])]),_:1}),t(d,{label:"封面（非必选）"},{default:o(()=>[t(O,{ref:"uploadRef","on-success":N,action:g(ae)(),"list-type":"picture-card","file-list":i.value,"onUpdate:fileList":a[2]||(a[2]=n=>i.value=n)},{file:o(({file:n})=>[u("img",{class:"el-upload-list__item-thumbnail",src:e.cover,alt:""},null,8,re),u("span",ce,[R.value?A("",!0):(h(),k("span",{key:0,class:"el-upload-list__item-delete",onClick:D},[t(p,null,{default:o(()=>[t(g(Y))]),_:1})]))])]),default:o(()=>[t(p,null,{default:o(()=>[t(g(Z))]),_:1})]),_:1},8,["action","file-list"]),u("div",pe,[t(v,{onClick:z,type:"primary",round:""},{default:o(()=>[I("  发布  ")]),_:1}),e.state===-1||e.state===0?(h(),$(v,{key:0,onClick:M,type:"warning",round:""},{default:o(()=>[I("  草稿  ")]),_:1})):A("",!0),t(v,{type:"warning",round:""},{default:o(()=>[I("  清空  ")]),_:1})])]),_:1}),t(d,{label:"正文（必填）",class:"div-98"})]),_:1},512)]),u("div",null,[t(j,{modelValue:e.text,"onUpdate:modelValue":a[3]||(a[3]=n=>e.text=n),height:"800px","disabled-menus":[],onUploadImage:F},null,8,["modelValue"])])])}}});const ke=P(me,[["__scopeId","data-v-6835c214"]]);export{ke as default};