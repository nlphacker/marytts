
$vowels = "a1, a2, a3, a4, a5, ai1, ai2, ai3, ai4, ai5, an1, an2, an3, an4, an5, ang1, ang2, ang3, ang4, ang5, ao1, ao2, ao3, ao4, ao5, e1, e2, e3, e4, e5, ei1, ei2, ei3, ei4, ei5, en1, en2, en3, en4, en5, eng1, eng2, eng3, eng4, eng5, er2, er3, er4, i1, i2, i3, i4, i5, ia1, ia2, ia3, ia4, ia5, ian1, ian2, ian3, ian4, ian5, iang1, iang2, iang3, iang4, iang5, iao1, iao2, iao3, iao4, iao5, ie1, ie2, ie3, ie4, ie5, in1, in2, in3, in4, in5, ing1, ing2, ing3, ing4, ing5, iong1, iong2, iong3, iong4, iong5, iu1, iu2, iu3, iu4, iu5, o1, o2, o3, o4, o5, ong1, ong2, ong3, ong4, ong5, ou1, ou2, ou3, ou4, ou5, u1, u2, u3, u4, u5, u:2, u:3, u:4, u:e4, ua1, ua2, ua3, ua4, ua5, uai1, uai2, uai3, uai4, uai5, uan1, uan2, uan3, uan4, uan5, uang1, uang2, uang3, uang4, uang5, ue1, ue2, ue3, ue4, ue5, ui1, ui2, ui3, ui4, ui5, un1, un2, un3, un4, un5, uo1, uo2, uo3, uo4, uo5";

$vowels =~ s/,//g;
@vowelarr = split(/\s+/, $vowels);

for ($i = 0; $i < $#vowelarr + 1; $i++)
{
    chomp($vowelarr[$i]);
    
    $notone = $vowelarr[$i];
    $notone =~ s/[1-5]//;
    #print $notone . "\n";

    $vlng = "s";
    if (length($notone) > 1)
    {
       $vlng = "l";
    }
  

    print "<vowel ph=\"$vowelarr[$i]\" vfront=\"1\" vheight=\"3\" vlng=\"$vlng\" vrnd=\"-\" />\n";
}

$consonants = "b, p, m, f, d, t, n, l, g, k, h, j, q, x, z, c, s, zh, ch, sh, r, y, w";
$consonants =~ s/,//g;
@consonantsarr = split(/\s+/, $consonants);

for ($i = 0; $i < $#consonantsarr + 1; $i++)
{
    chomp($consonantsarr[$i]);
      

    print "<consonant ph=\"$consonantsarr[$i]\" ctype=\"s\" cplace=\"l\" cvox=\"-\"/>\n";
}

