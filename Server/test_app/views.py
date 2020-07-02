from django.shortcuts import render

from rest_framework import viewsets
from test_app.models import Notice, Study, Subboard, Userinfo
from test_app.serializers import NoticeSerializer,StudySerializer,SubboardSerializer,UserinfoSerializer

# Create your views here.
class NoticeViewSet(viewsets.ModelViewSet):
    queryset = Notice.objects.all()
    serializer_class = NoticeSerializer

class StudyViewSet(viewsets.ModelViewSet):
    queryset = Study.objects.all()
    serializer_class = StudySerializer


class SubboardViewSet(viewsets.ModelViewSet):
    queryset = Subboard.objects.all()
    serializer_class = SubboardSerializer


class UserinfoViewSet(viewsets.ModelViewSet):
    queryset = Userinfo.objects.all()
    serializer_class = UserinfoSerializer
