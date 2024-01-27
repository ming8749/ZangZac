/**
 * 
 */
const url = 'https://twelvelovetype.netlify.app/';

function setShare(){
  var resultImg = document.querySelector('#resultImg');
  var resultAlt = resultImg.firstElementChild.alt;
  const shareTitle = '캠핑테스트 결과';
  const shareDes = infoList[resultAlt].name;
  const shareImage = url + 'img/image-' + resultAlt + '.png';
  const shareURL = url + 'page/result-' + resultAlt + '.html';

 Kakao.API.request({
  url: '/v1/api/talk/friends/message/default/send',
  data: {
    receiver_uuids: ['${RECEIVER_UUID}'],
    template_object: {
      object_type: 'feed',
      content: {
        title: '딸기 치즈 케익',
        description: '#케익 #딸기 #삼평동 #카페 #분위기 #소개팅',
        image_url:
          'https://mud-kage.kakao.com/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png',
        link: {
          web_url: 'https://developers.kakao.com',
          mobile_web_url: 'https://developers.kakao.com',
        },
      },
      item_content: {
        profile_text: 'Kakao',
        profile_image_url: 'https://mud-kage.kakao.com/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png',
        title_image_url: 'https://mud-kage.kakao.com/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png',
        title_image_text: 'Cheese cake',
        title_image_category: 'Cake',
        items: [
          {
            item: 'Cake1',
            item_op: '1000원',
          },
          {
            item: 'Cake2',
            item_op: '2000원',
          },
          {
            item: 'Cake3',
            item_op: '3000원',
          },
          {
            item: 'Cake4',
            item_op: '4000원',
          },
          {
            item: 'Cake5',
            item_op: '5000원',
          },
        ],
        sum: 'Total',
        sum_op: '15000원',
      },
      social: {
        like_count: 100,
        comment_count: 200,
      },
      buttons: [
        {
          title: '웹으로 보기',
          link: {
            mobile_web_url: 'https://developers.kakao.com',
            web_url: 'https://developers.kakao.com',
          },
        },
        {
          title: '앱으로 보기',
          link: {
            mobile_web_url: 'https://developers.kakao.com',
            web_url: 'https://developers.kakao.com',
          },
        },
      ],
    },
  },
})
  .then(function(response) {
    console.log(response);
  })
  .catch(function(error) {
    console.log(error);
  });
}