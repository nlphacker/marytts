$file = shift;

open(INFILE, $file);

while ($line = <INFILE>)
{
     chomp($line); 
     @a = split(/\s+/, $line);
     for ($i = 1; $i <= $#a; $i++)
     {
     	if (!defined($phone{$a[$i]}))
        {
        	$phone{$a[$i]} = 1;
        }  
        else
	{
		$phone{$a[$i]}++;
	}  

     }
}

close(INFILE);

 for (keys %phone)
{
	if ($phone{$_} < 5)
	{
		print "$_ $phone{$_}\n"
	}
}
