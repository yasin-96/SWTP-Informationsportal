<template>
  <b-container class="pt-4 pb-4">
    <b-button-group class="d-flex">
      <b-form-textarea :placeholder="nPlaceholder" :rows="nRows" :no-resize="nResize" :size="bTextSize"></b-form-textarea>
      <b-button>{{ bText }}</b-button>
    </b-button-group>
  </b-container>
</template>

<script>
export default {
  name: 'NewContent',
  props: {
    nPlaceholder: {
      type: String,
      default: 'asd',
    },
    nRows: {
      type: Number,
      default: 3,
    },
    bText: {
      type: String,
      default: 'Send',
    },
    nResize: {
      type: Boolean,
      default: true,
    },
    bTextSize: {
        type: String,
        default: 'sm'
    }
  },
};
</script>

<style></style>
