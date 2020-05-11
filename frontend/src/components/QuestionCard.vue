<template>
  <v-container>
    <v-card
      class="mx-auto"
      color="#26c6da"
      dark
      max-width="400"
    >
      <v-card-title>
        <v-icon
          large
          left
        >
          mdi-account
        </v-icon>
        <span class="title font-weight-light">
          <v-list-item-content>
            <v-list-item-title>User-Name</v-list-item-title>
          </v-list-item-content>
        </span>
      </v-card-title>

      <v-card-text class="headline font-weight-bold">
        Turns out semicolon-less style is easier and safer in TS because most gotcha edge cases are type invalid as well."
      </v-card-text>

      <v-card-actions>
        <v-list-item class="grow">
          <v-row
            align="center"
            justify="end"
          >
            <v-icon class="mr-1">
              mdi-heart
            </v-icon>
            <span class="subheading mr-2">256</span>
            <span class="mr-1">·</span>
          </v-row>
        </v-list-item>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
  export default {
    data: () => ({
      //
    }),
  }
</script>

<script>
export default {
    name: "QuestionCard",
    props: {
		qId: {
			type: String,
            default: "",
            length: 24
        },
        qContent: {
            type: String, 
            default: ""
        },
        qTags: {
            type: Array,
            default: Array
        },
        qDate: {
            type: String,
            default: ""
        }
	}
}
</script>

<style>

</style>