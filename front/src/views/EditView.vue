<script setup lang="ts">
import {ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const router = useRouter();


const props = defineProps({
  postId: {
    type: [Number, String],
    required: true,
  },
});

const post = ref({
  id:0,
  title:"",
  content:"",
});

axios.get(`/api/posts/${props.postId}`).then((response:any) => {
  post.value = response.data;
});

const edit = () => {
  axios.put(`/api/posts/${props.postId}`, post.value).then(() => {
    router.replace({name : "home"});
  });
}

</script>

<template>
  <div>
    <el-input type="text" v-model="post.title" />
  </div>

  <div>
    <el-input type="textarea" v-model="post.content" rows="15"></el-input>
  </div>

  <div class="mt-2">
    <el-button type="warning" @click="edit()">수정완료</el-button>
  </div>
</template>

<style>

</style>