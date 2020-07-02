from rest_framework import serializers

from test_app.models import Notice, Study, Subboard, Userinfo

class NoticeSerializer(serializers.ModelSerializer):
    class Meta:
        model = Notice
        fields = ('no','nickname','status','subboardnum',)


class StudySerializer(serializers.ModelSerializer):
    class Meta:
        model = Study
        fields = ('no','link','language',)

class SubboardSerializer(serializers.ModelSerializer):
    class Meta:
        model = Subboard
        fields = ('no','link','status','registernickname','registerno','requestnickname','language','contents','tax','israted',)

class UserinfoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Userinfo
        fields = ('no','id','password','nickname','points','money','requestnum','registernum','ratingcompleteness','ratingclarity','ratingtime','ratingcnt','noticecnt',)


