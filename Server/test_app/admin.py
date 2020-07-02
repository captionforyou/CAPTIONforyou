from django.contrib import admin

# Register your models here.

from .models import Notice, Study, Subboard, Userinfo


admin.site.register(Notice)
admin.site.register(Study)
admin.site.register(Subboard)
admin.site.register(Userinfo)
