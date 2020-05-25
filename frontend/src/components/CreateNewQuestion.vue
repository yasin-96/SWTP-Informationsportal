<template>
  <b-container class="mt-5">
    <b-card border-variant="white">
      <b-row
        class="justify-content-xs-top justify-content-sm-center justify-content-center"
      >
        <b-col xs="12" sm="12" md="12" lg="4">
          <b-card-img src="assets/new_question/3249754.png" alt="Image"></b-card-img>
        </b-col>
        <b-col>
          <b-card-body>
            <b-card-text>
              <b-container>
                <b-row
                  class="justify-content-xs-botton justify-content-sm-botton justify-content-md-center justify-content-lg-center"
                >
                  <b-col xs="12" sm="12" md="12" lg="10">
                    <!-- <div class="form-group"> -->
                    <h2>
                      <label for="questionHeader">Der Titel ihrer Frage</label>
                    </h2>
                    <b-form-input
                      type="text"
                      class="form-control"
                      id="questionHeader"
                      size="lg"
                      center
                      placeholder="Fragetitel"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="justify-content-center">
                  <b-col xs="12" sm="12" md="12" lg="10">
                    <b-form-textarea
                      id="textarea-large"
                      size="md"
                      rows="4"
                      max-rows="8"
                      :no-resize="true"
                      placeholder="Eine genauere Beschreibung ihrer Frage ..."
                    ></b-form-textarea>
                  </b-col>
                </b-row>
                <b-row class="justify-content-center">
                  <b-col xs="12" sm="12" md="12" lg="10"></b-col>
                </b-row>
              </b-container>
            </b-card-text>
          </b-card-body>
        </b-col>
      </b-row>
    </b-card>
  </b-container>
</template>

<script>

export default {
    name: "CreateNewQuestion",
    components: {},
    data: () => ({
        image: "@/assets/new_question/questions.svg"
    })
}
</script>

<style>
</style>