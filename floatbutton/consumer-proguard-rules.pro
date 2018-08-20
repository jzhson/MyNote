# keep getters/setters in RotatingDrawable so that animations can still work.
-keepclassmembers class top.bubblesun.floatbutton.FloatingActionsMenu$RotatingDrawable {
   void set*(***);
   *** get*();
}
