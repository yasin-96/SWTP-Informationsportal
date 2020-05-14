<!-- views/NotFound.vue -->
<template>
  <v-container class="404Error">
    <v-card class="mx-auto pa-2">
      <v-img class="white--text align-end pa-5" :max-height="maxHeight" :src="pageImage"> </v-img>

      <v-card-title class="display-4 justify-center">{{ pageError }}</v-card-title>
      <v-card-subtitle class="headline">{{ pageTitle }}</v-card-subtitle>
      <v-card-text>
        <v-list>
          <v-list-item v-for="l in listOfoptions" :key="l">
            <v-text class="body-2">
              <span>
                <v-icon color="danger">mdi-alert-box-outline</v-icon>
              </span>
              {{ l }}
            </v-text>
          </v-list-item>
        </v-list>
      </v-card-text>

      <v-card-actions class="justify-center">
        <v-btn 
          dark 
          v-for="(link, i) in urlOptions" 
          :key="i" 
          color="danger" 
          @click="goToLink(link.url)">
          {{ link.title }}
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: 'NotFound',
  data: () => ({
    maxHeight: '400px',
    pageImage: '@/assets/404/2703.jpg',
    pageError: '404',
    pageTitle: 'Die Seite kann nicht angezeigt werden, weil:',
    listOfoptions: [
      'Sie ein veraltetes Lesezeichen aufgerufen haben.',
      'Sie über eine Suchmaschine einen veralteten Index dieser Webseite aufgerufen haben.',
      'Sie eine falsche Adresse aufgerufen haben.',
      'Sie keinen Zugriff auf diese Seite haben.',
      'Die angefragte Quelle wurde nicht gefunden!',
      'Während der Anfrage ist ein Fehler aufgetreten!',
    ],
    urlOptions: [
      {
        title: 'Startseite',
        url: '/questions',
      },
      {
        title: 'Mtteilungen',
        url: '/notification'
      }
    ]
  }),
  methods: {
    goToLink(linkAddr) {
      console.log(linkAddr);
      this.$router.push(linkAddr);
    }
  }
};
</script>

<style>
</style>
