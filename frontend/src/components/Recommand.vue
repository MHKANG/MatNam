<template>
  <div>
      <template v-if="selectType==0">
          <div>
              <SelectMap 
                @next="locationNext"
              />
          </div>
      </template>
      <template v-if="selectType==1">
          <div>
              <SelectUserInfo
                @prev="infoPrev"
                @next="infoNext"
              />
          </div>
      </template>
      <template v-if="selectType==2">
          <div>
              <SelectUserPurpose
                @prev="purposePrev"
                @next="purposeNext"
              />
          </div>
      </template>
      <template v-if="selectType==3">
          <div>
              <Result 
                :loc="location"
                :pur="purpose"
              />
          </div>
      </template>
  </div>
</template> 

<script>
import SelectMap from '@/components/select/SelectMap.vue';
import SelectUserInfo from '@/components/select/SelectUserInfo.vue';
import SelectUserPurpose from '@/components/select/SelectUserPurpose.vue';
import Result from '@/components/select/Result.vue';

export default {
    components:{
        SelectMap,
        SelectUserInfo, 
        SelectUserPurpose,
        Result
    },
    data: () => {
        return {
            selectType: 0,
            location:"",
            purpose:"",
        }
    },
    methods: {
        locationNext(loc){
            this.selectType = 2;
            this.location = loc;
        },
        infoNext(){
            this.selectType = 2;
        },
        infoPrev(){
            this.selectType = 0;
        },
        purposeNext(pur){
            this.selectType = 3;
            this.purpose = pur;
        },
        purposePrev(){
            this.selectType = 0;
            this.purpose = 0;
        }
    },
}
</script>

<style>

</style>