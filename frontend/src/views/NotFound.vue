<!-- views/NotFound.vue -->
<template>
  <b-container class="mt-5">
      <b-jumbotron 
        :header="pageError" 
        :lead="pageTitle"
        bg-variant="rgb(223, 229, 230)"
        class="text-center"
      >
        <ul variant="danger" v-for="l in listOfoptions" :key="l" class="text-left">
          <li>
            <fai icon="exclamation-triangle" class="mr-3"></fai>
            {{ l }}
          </li>
        </ul>
        <b-container>
        <b-button variant="success" v-for="(link, i) in urlOptions" :key="i" @click="goToLink(link.url)"
          class="mt-3 mr-3"
        >
          {{ link.title }}
        </b-button>
        </b-container>
      </b-jumbotron>
  </b-container>
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
        url: '/notification',
      },
    ],
  }),
  methods: {
    goToLink(linkAddr) {
      console.log(linkAddr);
      this.$router.push(linkAddr);
    },
  },
};
</script>

<style>
ul {
  list-style: none;
}
</style>
