import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

/**
 * Define Socket options
 */
const apiAddress = process.env.VUE_APP_API_URL;
const websocketName = 'http://localhost:8082/info-portal-websocket';

const websocketSubcription = '/notify';
const stompEndPoint = '/socket/hello';

const websocketAddress = `${apiAddress}/${websocketName}`;

console.log('apiAddress:', apiAddress);
console.log('websocketName:', websocketName);
console.log('websocketAddress:', websocketAddress);
console.log('websocketSubcription:', websocketSubcription);
console.log('stompEndPoint:', stompEndPoint);

export default {
  data() {
    return {
      clientConnection: false,
      socket: null,
      stompClient: null,
    };
  },
  methods: {
    send(idOfQuestion) {
      console.log('Send message:' + idOfQuestion);
      if (this.stompClient && this.stompClient.connected) {
        console.warn('send to msg');
        this.stompClient.send(stompEndPoint, idOfQuestion, {});
      }
    },
    connect() {
      this.socket = new SockJS(websocketName);
      this.stompClient = Stomp.over(this.socket);
      console.warn('SOCKT connect to ', this.socket);
      console.log('CON:', this.clientConnection);
      this.stompClient.connect(
        {},
        (frame) => {
          this.clientConnection = true;
          console.log('CON:', this.clientConnection);
          console.log('BIN FRAME', frame);

          this.stompClient.subscribe(websocketSubcription, (response) => {
            console.log('WS:', JSON.parse(`${response.body}`));
          });
        },
        (error) => {
          console.log(error);
          this.clientConnection = false;
        }
      );
    },
    disconnect() {
      if (stompClient) {
        this.stompClient.disconnect();
      }
      this.clientConnection = false;
    },
    tickleConnection() {
      this.clientConnection ? this.disconnect() : this.connect();
    },
  },
};
