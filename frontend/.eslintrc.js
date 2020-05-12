module.exports = {
  extends: [
    // add more generic rulesets here, such as:
    // 'eslint:recommended',
    // 'plugin:prettier/recommended',
    'plugin:vue/vue3-recommended',
  ],
  rules: {
    'vue/no-parsing-error': [
      'error',
      {
        'nested-comment': false
      }
    ],
    'no-useless-escape': 0,
    'no-console': 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off'
  }
}