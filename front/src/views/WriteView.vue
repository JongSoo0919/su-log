<script setup lang="ts">
import {ref} from "vue";

const count = ref(0)
</script>

<template>
  <div>
    <el-input type="text" placeholder="제목을 입력해주세요."/>
  </div>

  <div>
    <el-input type="textarea" rows="15"></el-input>
  </div>

  <div class="mt-2">
    <el-button type="primary">글 작성완료</el-button>
  </div>
</template>

<style>

</style>